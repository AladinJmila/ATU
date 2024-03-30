def encrypt(plain_text, offset):
  cipher_text = ''

  for ch in plain_text:
    numerical_val = ord(ch)

    if (numerical_val == 32):
      cipher_text += ch
    elif (ch.isupper()):
      adjusted = ((numerical_val + offset - 65) % 26) + 65
      cipher_text += chr(adjusted)
    else:
      adjusted = ((numerical_val + offset - 97) % 26) + 97
      cipher_text += chr(adjusted)
  
  return cipher_text

print(encrypt('Isnt Java Great?', 4))
print(encrypt('ABCZ', 4))
print(encrypt('Hello', 4))