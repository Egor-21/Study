from django.contrib import admin
from .models import CustomUser, Category, Question, Test, Review, TestSumission

admin.site.register(CustomUser)
admin.site.register(Category)
admin.site.register(Question)
admin.site.register(Test)
admin.site.register(Review)
admin.site.register(TestSumission)