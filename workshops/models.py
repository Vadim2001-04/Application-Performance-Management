# workshops/models.py
from django.db import models
from django.conf import settings
from django.core.exceptions import ValidationError
from django.utils import timezone


class Workshop(models.Model):
    title = models.CharField(max_length=200, verbose_name="Название")
    description = models.TextField(verbose_name="Описание")
    instructor = models.CharField(max_length=200, verbose_name="Инструктор")
    date_time = models.DateTimeField(verbose_name="Дата и время")
    duration = models.IntegerField(help_text="Длительность в минутах", verbose_name="Длительность")
    max_participants = models.IntegerField(verbose_name="Максимум участников")
    price = models.DecimalField(max_digits=10, decimal_places=2, verbose_name="Цена")
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = 'Мастер-класс'
        verbose_name_plural = 'Мастер-классы'
        ordering = ['date_time']

    def __str__(self):
        return f"{self.title} - {self.date_time}"

    def get_available_spots(self):
        return self.max_participants - self.bookings.filter(status='active').count()

    def clean(self):
        if self.date_time < timezone.now():
            raise ValidationError('Нельзя создать мастер-класс в прошлом')


class Booking(models.Model):
    user = models.ForeignKey(
        settings.AUTH_USER_MODEL,
        on_delete=models.CASCADE,
        related_name='bookings',
        verbose_name="Пользователь"
    )
    workshop = models.ForeignKey(
        Workshop,
        on_delete=models.CASCADE,
        related_name='bookings',
        verbose_name="Мастер-класс"
    )
    created_at = models.DateTimeField(auto_now_add=True)
    status = models.CharField(
        max_length=20,
        choices=[('active', 'Active'), ('cancelled', 'Cancelled')],
        default='active'
    )

    class Meta:
        verbose_name = 'Бронирование'
        verbose_name_plural = 'Бронирования'
        ordering = ['-created_at']

    def __str__(self):
        return f"{self.user.username} - {self.workshop.title}"

    def clean(self):
        if Booking.objects.filter(
                user=self.user,
                workshop=self.workshop,
                status='active'
        ).exclude(pk=self.pk).exists():
            raise ValidationError('Вы уже забронировали это место')

        if self.workshop.date_time < timezone.now():
            raise ValidationError('Нельзя забронировать прошедший мастер-класс')