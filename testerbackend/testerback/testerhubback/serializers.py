from rest_framework import serializers
from django.contrib.auth import authenticate
from .models import *


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = CustomUser
        fields = ['email', 'nickname', 'password']
        extra_kwargs = {'password': {'write_only':True}}

    def create(self, validated_data):
        user = CustomUser.objects.create_user(
            email = validated_data['email'],
            nickname = validated_data['nickname'],
            password = validated_data['password']
        )
        return user
    

    
class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category
        fields = "__all__"

class QuestionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Question
        fields = "__all__"
    
class TestSerializer(serializers.ModelSerializer): 
    category = CategorySerializer()
    

    class Meta:
        model = Test
        fields = ["id", "title", "category", "time", "difficult", "image"]



class ReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = Review
        fields = "__all__"

class TestDetailSerializer(serializers.ModelSerializer):
    category = CategorySerializer()
    reviews = ReviewSerializer(many = True,source = 'review_set',read_only = True)

    class Meta:
        model = Test
        fields = ["title", "description", "difficult", "reviews", "category"]

class TestsQuestionsSerializer(serializers.ModelSerializer):
    question = QuestionSerializer(many = True, read_only = True)
    class Meta:
        model = Question
        fields = "__all__"

class TestSubmissionSerializer(serializers.ModelSerializer):
    class Meta:
        model = TestSumission
        fields = '__all__'
    
    def create(self, validated_data):
        test = validated_data['test']
        submitted_answers = validated_data['submitted_answers']
        correct_answers = test.questions.values_list('correct_answers', flat=True)

        total_score = 0

        for submitted_answer, correct_answer in zip(submitted_answers, correct_answers):
            if submitted_answer in correct_answer:  # Проверяем, содержится ли предоставленный ответ в списке правильных ответов
                question = test.questions.filter(correct_answers__contains=[submitted_answer]).first()
                total_score += question.points
        validated_data['score'] = total_score

        return super().create(validated_data)

