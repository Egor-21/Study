from django.shortcuts import render
from rest_framework import generics
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from .models import *
from .serializers import *
from django_filters.rest_framework import DjangoFilterBackend, FilterSet
from rest_framework.permissions import IsAuthenticated
from django.contrib.auth import login


class RegistrationAPIView(APIView):
    def post(self, request):
        serializer = UserSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        

    
class TestListAPIView(generics.ListAPIView):
    queryset = Test.objects.all()
    serializer_class = TestSerializer
    filter_backends = [DjangoFilterBackend]
    filterset_fields = ['category', 'difficult']

class TestDetailAPIView(generics.RetrieveAPIView):
    queryset = Test.objects.all()
    serializer_class = TestDetailSerializer

    def get_queryset(self):
        queryset = super().get_queryset()
        test_id = self.kwargs.get('pk')
        return queryset.prefetch_related('review_set')
        

class TestQuestionsAPIView(generics.ListAPIView):
    serializer_class = QuestionSerializer

    def get_queryset(self):
        test_id = self.kwargs['pk']
        return Question.objects.filter(test__id = test_id)
    
class TestSubmissionAPIView(APIView):
    def post(self, request, pk):
        test = Test.objects.get(pk=pk)
        user = request.user
        submitted_answers = request.data.get('submitted_answers', []) 

        serializer = TestSubmissionSerializer(data={'user': user.id, 'test': test.id, 'submitted_answers': submitted_answers})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    

