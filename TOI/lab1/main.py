class Interval:
    def __init__(self):
        self.left = 0
        self.right = 0


class IntervalDecode(Interval):
    def __init__(self):
        super().__init__()
        self.symbol = None  # Символ, связанный с интервалом


def init_interval(symbols, chance):  # Функция инициализации интервалов для каждой буквы
    l = 0
    intervals = {}

    for i in range(len(symbols)):
        intervals[symbols[i]] = Interval()
        intervals[symbols[i]].left = l
        intervals[symbols[i]].right = l + chance[i]
        l = intervals[symbols[i]].right
    return intervals


def arithmetic_encoding(symbols, chance, text):  # Функция арифметического кодирования, входные данные: список символов, их вероятность и исходной текст
    intervals = init_interval(symbols, chance)
    left = 0.0
    right = 1.0

    for i in range(len(text)):
        symb = text[i]
        new_right = left + (right - left) * intervals[symb].right
        new_left = left + (right - left) * intervals[symb].left
        left = new_left
        right = new_right

    return (left + right) / 2


def init_intervals_decode(symbols, chance): # Функция инициализации интервалов букв для декодирования
    l = 0
    intervals = []

    for i in range(len(symbols)):
        interval = IntervalDecode()
        interval.left = l
        interval.right = l + chance[i]
        interval.symbol = symbols[i]
        intervals.append(interval)
        l = interval.right
    return intervals


def arithmetic_decode(symbols, chance, code, n):  # Функция декодирования кода
    intervlas = init_intervals_decode(symbols, chance)
    text = ""

    for i in range(n):
        for j in range(len(symbols)):
            if intervlas[j].left <= code < intervlas[j].right:
                text += intervlas[j].symbol
                code = (code - intervlas[j].left) / (intervlas[j].right - intervlas[j].left)
                break
    return text


string = "abaabaaca!"
print("Исходная строка:", string)
# Считаем частоту букв в строке

symbol_range = {}

for i in string:
    if i not in symbol_range.keys():
        symbol_range[i] = 1
    else:
        symbol_range[i] += 1

# Вычисление вероятностей появления букв

symbol_chance = {}

for key, value in symbol_range.items():
    symbol_chance[key] = value / len(string)

symbols = list(symbol_range.keys())
chances = list(symbol_chance.values())

# Кодирование строки

encode = arithmetic_encoding(symbols, chances, string)
print("Закодировано:", encode)

decode = arithmetic_decode(symbols, chances, encode, len(string))
print("Раскодировано:", decode)


