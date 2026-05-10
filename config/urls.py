from django.contrib import admin
from django.urls import path, include
from rest_framework_simplejwt.views import TokenRefreshView
from workshops.views import (
    WorkshopViewSet, BookingViewSet,
    UserRegistrationView, CustomTokenObtainPairView
)
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register(r'workshops', WorkshopViewSet, basename='workshop')
router.register(r'bookings', BookingViewSet, basename='booking')

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/token/', CustomTokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/register/', UserRegistrationView.as_view({'post': 'create'}), name='register'),
    path('api/', include(router.urls)),
]