from django.urls import path
from . import views
from .views import *
urlpatterns = [
    path('register/', views.RegistrationAPIView.as_view(), name='register'),
    path('tests/', TestListAPIView.as_view(), name = 'test-list'),
    path('tests/<int:pk>/', TestDetailAPIView.as_view(), name = 'test-detail'),
    path('tests/<int:pk>/questions/', TestQuestionsAPIView.as_view(), name = 'test-questions'),
    path('tests/<int:pk>/questions/sub/', TestSubmissionAPIView.as_view(), name = 'test-submission')
]