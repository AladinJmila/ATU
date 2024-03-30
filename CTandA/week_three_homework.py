import math

# 1 . Write an algorithm that returns the largest element in an array. Assume the array is unsorted
# input: Unsorted array of intergers (assuming they are integers)
# output: Largest element in the array

# Iterate through the array from start to end. 
# Save the first element in a variable called "largest".
# Compare the "largest" to the current value and if it's bigger it gets saved in the variable instead
# Return "largest"

arr = [1, 6, 4, 87, 2, 66, 47, 101, 33, 78]

def find_largest(arr):
  largest = arr[0]
  for num in arr:
    if num > largest:
      largest = num
  return largest

# print(find_largest(arr))

# ***************************************************

# 2. Write an algorithm that returns a new array which is the reverse of the input
# input: array of elements
# output: a new array reversed

# Iterate throught the input array from the end and add each item a new array

def reverse(arr):
  reversed = []
  for i in range(len(arr)):
    reversed.append(arr[len(arr) - 1 - i])

  return reversed

# print(reverse(arr))

# ***************************************************

# 3. Write an algorithm that checks whether an element occurs in an array.
# input: array of elements, element to check
# output: boolean

# Iterate through the input array and compare each item to the input value
# if the value is found, return True and leave, else if all elements are checked and no match found return False

def contains(arr, value):
  for x in arr:
    if x == value:
      return True
  
  return False

# print(contains(arr, 66))

# ***************************************************

# 4. Write an algorithm that returns the lements on odd positions in an array.
# input: array of values
# output: elements on odd positions in input array (possibly a new array)

# Iterate through the input array and find elements of odd index and save them in a new array

def odd_index(arr):
  odd_indexed = []
  for i in range(len(arr)):
    if i % 2 != 0:
      odd_indexed.append(arr[i])

  return odd_indexed

# print(odd_index(arr))

# ***************************************************

# 5. Write and algorithm that computes the running total of an array of numbers
# input: array of number
# output: total sum of numbers

# Iterate through all entries an sum their values

def get_sum(arr):
  sum = 0
  for x in arr:
    sum += x

  return sum

# print(get_sum(arr))
# print(sum(arr))

# ***************************************************

# 6. Write an algorithm that prints a multiplication table for numbers up to 12
# input: number
# output: multiplication table for the number up to 12

# Using a while loop, multiply input with numbers from 1 to 12

def to_twelve_multiplication(num):
  i = 1
  while i <= 12:
    print(i * num)
    i += 1

# to_twelve_multiplication(5)
    
# ***************************************************
    
# 7. Write an algorithm that prints the first 100 prime numbers
# input: no input
# output: 100 first prime numbers
    
# keeping a counter of the elements printed and stopping at 100, start from 2 and check whether the number is prime

def is_prime(num):
    if num == 2:
      return True
    
    for i in range(2, math.ceil(math.sqrt(num)) + 1):
      if num % i == 0:
        return False

    return True

def prime_100():
  counter = 0
  num = 2
 
  while counter < 100:
    if (is_prime(num)):
      print(num)
      counter += 1
    
    num += 1

# print(is_prime(9))
# prime_100()

# ***************************************************

# 8. Write an algorithm that prints the numbers from 1 to 100 and for multiple of 3 "Fizz" instead of the number and for the mulitples of 5 "Buzz"
# input: no input
# output: number or Fizz or Buzz
    
# From 1 to 100, check if the number is divisible by 3, print Fizz, if it's divisible by 5 print Buzz else print the number
    
def fizz_buzz_100():
  for i in range(1, 101):
    if (i % 3 == 0):
      print('Fizz')
    elif (i % 5 == 0):
      print('Buzz')
    else:
      print(i)

fizz_buzz_100()
