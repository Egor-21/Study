from django.db import models
from django.contrib.postgres.fields import ArrayField
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager

class CustomUserManager(BaseUserManager):
    def create_user(self, email, nickname, password=None, **extra_fields):
        if not email:
            raise ValueError('The Email field must be set')
        email = self.normalize_email(email)
        user = self.model(email=email, nickname=nickname, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, nickname, password=None, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)
        return self.create_user(email, nickname, password, **extra_fields)

class CustomUser(AbstractBaseUser):
    email = models.EmailField("Почта", unique=True)
    nickname = models.CharField("Никнейм", max_length=255, unique=True)
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    image = models.ImageField("Изображение", upload_to="userimg/")
    

    objects = CustomUserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['nickname']

    class Meta:
        verbose_name = "Пользователь"
        verbose_name_plural = "Пользователи"

    def __str__(self):
        return self.email
    
class Category(models.Model):
    title = models.CharField("Название", max_length=50)

    def __str__(self):
        return self.title
    
    class Meta:
        verbose_name = "Категория"
        verbose_name_plural = "Категории"

class Question(models.Model):
    text = models.TextField("Текст вопроса")
    category = models.ForeignKey(Category, on_delete=models.CASCADE, verbose_name="Категория вопроса")
    answers = ArrayField(models.CharField(max_length=100))
    correct_answers = ArrayField(models.CharField(max_length=100))
    points = models.SmallIntegerField("Кол-во очков за правильный ответ")
    image = models.ImageField("Изображение", upload_to="quesimg/")

    def __str__(self):
        return f"{self.category} - {self.points}"
    
    class Meta:
        verbose_name = "Вопрос"
        verbose_name_plural = "Вопросы"

class Test(models.Model):
    DIFFICULTY_CHOICES = [
        ('easy', 'Лёгкий'),
        ('normal', 'Нормальный'),
        ('hard', 'Тяжёлый')
    ]
    title = models.TextField("Название теста")
    description = models.TextField("Описание теста")
    category = models.ForeignKey(Category, on_delete=models.CASCADE, verbose_name="Категория теста")
    time = models.SmallIntegerField("Время на выполнение")
    difficult = models.CharField("Сложность", max_length=50, choices=DIFFICULTY_CHOICES)
    questions = models.ManyToManyField(Question, verbose_name="Вопросы")
    image = models.ImageField("Изображение", upload_to="testsimg/", blank=True, null=True)

    class Meta:
        verbose_name = "Тест"
        verbose_name_plural = "Тесты"

    def __str__(self):
        return f"{self.title} - {self.difficult}"
    

class Review(models.Model):
    user = models.ForeignKey(CustomUser, on_delete=models.CASCADE, verbose_name="Пользователь")
    test = models.ForeignKey(Test, on_delete=models.CASCADE, verbose_name="Тест")
    text = models.TextField("Текст отзыва") 

    class Meta:
        verbose_name = "Отзыв"
        verbose_name_plural = "Отзывы"

    def __str__(self):
        return f"{self.user.email} - {self.test.title}"
    
class TestSumission(models.Model):
    user = models.ForeignKey(CustomUser, on_delete = models.CASCADE, verbose_name = "Пользователь")
    test = models.ForeignKey(Test, on_delete = models.CASCADE, verbose_name="Тест")
    submitted_answers = ArrayField(models.CharField(max_length=100, verbose_name = "Отправленные ответы"))
    score = models.FloatField("Оценка", null=True, blank = True)
    

    class Meta:
        verbose_name = "Результат теста"
        verbose_name_plural = "Результаты теста"
    
    def __str__(self):
        return f"{self.user} - {self.test}"
    
    