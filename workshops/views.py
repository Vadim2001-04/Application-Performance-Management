# workshops/views.py

from rest_framework import viewsets, status, permissions
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework_simplejwt.views import TokenObtainPairView
from django.utils import timezone
from django.db import transaction

from .models import Workshop, Booking
from .serializers import WorkshopSerializer, WorkshopDetailSerializer, BookingSerializer
from users.serializers import UserRegistrationSerializer
from users.models import User


class IsAdminUser(permissions.BasePermission):
    def has_permission(self, request, view):
        return request.user and request.user.is_admin()


class CustomTokenObtainPairView(TokenObtainPairView):
    pass


class UserRegistrationView(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserRegistrationSerializer
    permission_classes = [permissions.AllowAny]
    http_method_names = ['post']


class WorkshopViewSet(viewsets.ModelViewSet):
    queryset = Workshop.objects.all()


    def get_serializer_class(self):
        if self.action == 'retrieve':
            return WorkshopDetailSerializer
        return WorkshopSerializer

    def get_permissions(self):
        if self.action in ['create', 'update', 'partial_update', 'destroy']:
            return [permissions.IsAuthenticated(), IsAdminUser()]
        return [permissions.AllowAny()]

    def get_queryset(self):
        queryset = Workshop.objects.all()

        date_filter = self.request.query_params.get('date', None)
        if date_filter:
            queryset = queryset.filter(date_time__date=date_filter)

        if self.action == 'list':
            queryset = queryset.filter(date_time__gte=timezone.now())

        return queryset.select_related().prefetch_related('bookings')


class BookingViewSet(viewsets.ModelViewSet):
    serializer_class = BookingSerializer
    permission_classes = [permissions.IsAuthenticated]  # ← БЕЗ СКОБОК!

    def get_queryset(self):
        return Booking.objects.filter(
            user=self.request.user
        ).select_related('workshop', 'user')

    @transaction.atomic
    def perform_create(self, serializer):
        workshop = serializer.validated_data['workshop']
        user = self.request.user

        if Booking.objects.filter(user=user, workshop=workshop, status='active').exists():
            from rest_framework.exceptions import ValidationError
            raise ValidationError({"error": "Вы уже забронировали это место"})

        if workshop.get_available_spots() <= 0:
            from rest_framework.exceptions import ValidationError
            raise ValidationError({"error": "Нет доступных мест"})

        if workshop.date_time < timezone.now():
            from rest_framework.exceptions import ValidationError
            raise ValidationError({"error": "Нельзя забронировать прошедший мастер-класс"})

        serializer.save(user=user)

    @action(detail=False, methods=['get'])
    def my_bookings(self, request):
        bookings = self.get_queryset()
        serializer = self.get_serializer(bookings, many=True)
        return Response(serializer.data)

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        if instance.status == 'cancelled':
            return Response(
                {"error": "Бронирование уже отменено"},
                status=status.HTTP_400_BAD_REQUEST
            )
        instance.status = 'cancelled'
        instance.save()
        return Response({"message": "Бронирование отменено"}, status=status.HTTP_200_OK)