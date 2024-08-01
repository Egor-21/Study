from keras.datasets import mnist #импортируем набор MNIST
from keras.utils.vis_utils import plot_model
from keras import backend as keras_backend #импортируем настройки керас
from tensorflow.keras.utils import to_categorical
from keras.models import Sequential
from keras.layers import Dense
(X_train, y_train), (X_test, y_test) = mnist.load_data()
print('X_train: ' + str(X_train.shape))
print('Y_train: ' + str(y_train.shape))
print('X_test: ' + str(X_test.shape))
print('Y_test: ' + str(y_test.shape))
from matplotlib import pyplot
for i in range(9):
 pyplot.subplot(330 + 1 + i)
 pyplot.imshow(X_train[i], cmap=pyplot.get_cmap('gray'))
pyplot.show()
print('start of y_train:', y_train[:10])
image_height = X_train.shape[1]
image_width = X_train.shape[2]
number_of_pixels = image_height * image_width
print(number_of_pixels)
# конвертирование в плавающую точку
X_train = keras_backend.cast_to_floatx(X_train)
X_test = keras_backend.cast_to_floatx(X_test)
# масштабирование данных в интервал [0, 1]
X_train /= 255.0
X_test /= 255.0
print(y_train[:5])
y_train[:5]
import numpy as np
number_of_classes = int(1 + max(np.append(y_train, y_test)))
number_of_classes
y_train = to_categorical(y_train, num_classes=number_of_classes)
y_test = to_categorical(y_test, num_classes=number_of_classes)
y_train[:5]
X_train.shape[0]
X_train = np.reshape(X_train, [X_train.shape[0], number_of_pixels])
X_test = np.reshape(X_test, [X_test.shape[0], number_of_pixels])
X_train = X_train.reshape([X_train.shape[0], number_of_pixels])
X_test = X_test.reshape([X_test.shape[0], number_of_pixels])
X_train[100][10:]


model = Sequential()
dense_layer = Dense(number_of_pixels, activation='relu',
input_shape=[number_of_pixels])
model.add(dense_layer)
model.add(Dense(number_of_classes, activation='softmax'))
model.summary()
plot_model(model, to_file='model_plot.png', show_shapes=True,
show_layer_names=True)
# компиляция модели для превращения ее из спецификации в код
#
model.compile(loss='categorical_crossentropy',optimizer='adam',metrics=['accuracy'])
model.compile(loss='categorical_crossentropy',optimizer='adam',metrics=['accuracy'])
history = model.fit(X_train, y_train, validation_data=(X_test, y_test),
epochs=30, batch_size=256, verbose=2)
history = model.fit(X_train, y_train, validation_split=0.25, epochs=10, batch_size=256, verbose=1)
import matplotlib.pyplot as plt
plt.plot(history.history['loss'])
plt.plot(history.history['val_loss'])
plt.title('Model loss')
plt.ylabel('loss')
plt.xlabel('Epoch')
plt.legend(['Train', 'Test'], loc='upper left')
import matplotlib.pyplot as plt1
plt1.plot(history.history['accuracy'])
plt1.plot(history.history['val_accuracy'])
plt.title('Model accuracy')
plt.ylabel('accuracy')
plt.xlabel('Epoch')
plt.legend(['Train', 'Test'], loc='upper left')