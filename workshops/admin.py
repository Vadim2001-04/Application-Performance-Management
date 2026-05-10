from django.contrib import admin
from .models import Workshop, Booking

@admin.register(Workshop)
class WorkshopAdmin(admin.ModelAdmin):
    list_display = ('title', 'instructor', 'date_time', 'max_participants')
    list_filter = ('date_time', 'instructor')

@admin.register(Booking)
class BookingAdmin(admin.ModelAdmin):
    list_display = ('user', 'workshop', 'status', 'created_at')
    list_filter = ('status', 'created_at')