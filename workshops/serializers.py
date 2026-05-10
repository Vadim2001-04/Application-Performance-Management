from rest_framework import serializers
from .models import Workshop, Booking
from users.serializers import UserSerializer


class WorkshopSerializer(serializers.ModelSerializer):
    available_spots = serializers.SerializerMethodField()

    class Meta:
        model = Workshop
        fields = [
            'id', 'title', 'description', 'instructor',
            'date_time', 'duration', 'max_participants',
            'price', 'available_spots', 'created_at', 'updated_at'
        ]
        read_only_fields = ['created_at', 'updated_at']

    def get_available_spots(self, obj):
        return obj.get_available_spots()


class WorkshopDetailSerializer(WorkshopSerializer):
    bookings_count = serializers.SerializerMethodField()

    class Meta(WorkshopSerializer.Meta):
        fields = WorkshopSerializer.Meta.fields + ['bookings_count']

    def get_bookings_count(self, obj):
        return obj.bookings.filter(status='active').count()


class BookingSerializer(serializers.ModelSerializer):
    user = UserSerializer(read_only=True)
    workshop = WorkshopSerializer(read_only=True)
    workshop_id = serializers.PrimaryKeyRelatedField(
        queryset=Workshop.objects.all(),
        source='workshop',
        write_only=True
    )

    class Meta:
        model = Booking
        fields = ['id', 'user', 'workshop', 'workshop_id', 'created_at', 'status']
        read_only_fields = ['user', 'created_at', 'status']