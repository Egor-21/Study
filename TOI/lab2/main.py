# Считаем сколько раз встречается символ в сообщении
def create_count_table(text):
    count_table = {}
    for char in text:
        if char in count_table:
            count_table[char] += 1
        else:
            count_table[char] = 1
    return count_table


# Функция для сортировки узлов по частоте
def sort_by_count(node):
    return node["count"]


# Функция построения дерева Хоффмана
def build_tree(count_table):
    nodes = []
    for symbol, count in count_table.items():
        nodes.append({"symbol": symbol, "count": count})
    while len(nodes) > 1:
        nodes.sort(key=sort_by_count)
        left = nodes.pop(0)
        right = nodes.pop(0)
        parent = {"count": left["count"] + right["count"], "left": left, "right": right}
        nodes.append(parent)
    return nodes[0]


# Присваение бинарных кодов символам
def set_binary_code(node, code="",codes={}):
    if "symbol" in node:
        codes[node["symbol"]] = code
    else:
        set_binary_code(node["left"], code+"0", codes)
        set_binary_code(node["right"], code+"1", codes)
    return codes


def encode_mes(text, codes):
    encode_text = ""
    for char in text:
        encode_text += codes[char]
    return encode_text


def decode_messege(encode_mes, root):
    decoded_mes = ""
    curr_node = root
    for i in encode_mes:
        if i == "0":
            curr_node = curr_node["left"]
        else:
            curr_node = curr_node["right"]
        if "symbol" in curr_node:
            decoded_mes += curr_node["symbol"]
            curr_node = root
    return decoded_mes


text = str(input("Введите сообщение: "))

count_table = create_count_table(text)
huffman_tree = build_tree(count_table)
codes = set_binary_code(huffman_tree)
encoded_mes = encode_mes(text, codes)
decode_mes = decode_messege(encoded_mes, huffman_tree)

print("Исходное сообщение:", text)
print(codes)
print("Результат кодирования:", encoded_mes)
print("Результат декодирования:", decode_mes)

