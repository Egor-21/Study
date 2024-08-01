import numpy as np
import matplotlib.pyplot as plt
from keras.datasets import mnist
from keras import models
from keras import layers
from keras.utils import to_categorical

# Загрузка данных
(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

# Предобработка данных
train_images = train_images.reshape((60000, 28 * 28)).astype('float32') / 255
test_images = test_images.reshape((10000, 28 * 28)).astype('float32') / 255

# Преобразование меток
train_labels = to_categorical(train_labels)
test_labels = to_categorical(test_labels)

# Создание модели
model = models.Sequential()
model.add(layers.Dense(64, activation='relu', input_shape=(28 * 28,)))
model.add(layers.Dense(64, activation='relu'))
model.add(layers.Dense(10, activation='softmax'))  # 10 классов в MNIST

model.compile(optimizer='rmsprop',
              loss='categorical_crossentropy',
              metrics=['accuracy'])

# Создание проверочного набора
x_val = train_images[:10000]
partial_x_train = train_images[10000:]
y_val = train_labels[:10000]
partial_y_train = train_labels[10000:]

# Обучение модели
history = model.fit(partial_x_train,
                    partial_y_train,
                    epochs=20,
                    batch_size=512,
                    validation_data=(x_val, y_val))

# Отслеживание обучения, визуализация
history_dict = history.history
loss_values = history_dict['loss']
val_loss_values = history_dict['val_loss']
epochs = range(1, len(loss_values) + 1)

plt.plot(epochs, loss_values, 'ro', label='Ошибка на обучающем наборе')
plt.plot(epochs, val_loss_values, 'b', label='Ошибка на проверочном наборе')
plt.title('Ошибка на обучении и проверке')
plt.xlabel('Эпохи')
plt.ylabel('Ошибка')
plt.legend()
plt.show()

# График точности
acc_values = history_dict['accuracy']
val_acc_values = history_dict['val_accuracy']

plt.plot(epochs, acc_values, 'ro', label='Точность на обучающем наборе')
plt.plot(epochs, val_acc_values, 'b', label='Точность на проверочном наборе')
plt.title('Точность на обучении и проверке')
plt.xlabel('Эпохи')
plt.ylabel('Точность')
plt.legend()
plt.show()

# Функция для вывода предсказаний и изображений
def show_predictions(test_images, test_labels, num_examples=5):
    for i in range(num_examples):
        # Выбор случайного изображения из тестового набора
        image_index = np.random.randint(0, len(test_images))
        test_image = test_images[image_index]
        true_label = np.argmax(test_labels[image_index])

        # Проведение предсказания для выбранного изображения
        prediction = model.predict(np.expand_dims(test_image, axis=0))
        predicted_label = np.argmax(prediction)

        # Вывод информации о предсказании и истинной метке
        print(f"Пример {i + 1}:")
        print("Истинная метка:", true_label)
        print("Предсказанная метка:", predicted_label)

        # Вывод изображения и его предсказанной метки
        plt.imshow(test_image.reshape(28, 28), cmap='binary')
        plt.xlabel(f"Предсказанная метка: {predicted_label}")
        plt.show()

# Вывод нескольких предсказаний и соответствующих изображений
show_predictions(test_images, test_labels, num_examples=5)
