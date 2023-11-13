# Exercise 1
# create a  10 elements list 
# return the sublist [3-8]
# and print it in reverse order

my_list = list(range(10))


def sublist_and_reverse(a_list, index1, index2):
    sub_list = a_list[index1:index2]
    sub_list.reverse()  # Reverse the sublist
    return sub_list


result = sublist_and_reverse(my_list, 3, 8)
print(result)

# exercise 2
# create a function taking two numbers
# and returning each of the values multiplied by 2
def multiply(num1, num2):
    """
    this function takes two numbers and multiples them by 2
    :param num1: a number
    :param num2: a number
    :return: the two numbers multiplied by 2
    """
    # ??? return the numbers multiplied

def multiply(num1, num2):
    # Multiply the numbers by 2
    num1 *= 2
    num2 *= 2
    return num1, num2


# Example usage:
(res1, res2) = multiply(3, 5)
print(res1, res2)

# exercise 3
# create a dictionary with name, surname, dob, age
# print all the key/value pairs

my_dict = {
    'name': 'John',
    'surname': 'Doe',
    'dob': '1990-01-01',
    'age': 30
}


def print_dict_values(a_dict):
    for key, value in a_dict.items():
        print(f"{key}: {value}")


print_dict_values(my_dict)


# exercise 4
# remove the key "name" in the dictionary above

my_dict = {
    'name': 'John',
    'surname': 'Doe',
    'dob': '1990-01-01',
    'age': 30
}


def remove_key_from_dict(a_dict):
    if 'name' in a_dict:
        del a_dict['name']
    return a_dict


result = remove_key_from_dict(my_dict)
print(result)



# exercise 5
# consider the following list:
# [1, 2, 3, 4, 0, 1, 2, 0, 3]
# print only the elements that are true

def print_true_elements(a_list):
    true_elements = [element for element in a_list if element]
    print(true_elements)


my_list = [1, 2, 3, 4, 0, 1, 2, 0, 3]
print_true_elements(my_list)



# exercise 6
# join the following lists
# [1, 2, 3, 4] [0, 1, 2, 0, 3]
# then sort the resulting list
# remove all elements that are <2

def join_and_sort_lists(list_1, list_2):
    combined_list = list_1 + list_2  # Join the lists
    sorted_list = sorted(combined_list)  # Sort the list
    res = [element for element in sorted_list if element >= 2]  # Remove elements < 2
    return res


list1 = [1, 2, 3, 4]
list2 = [0, 1, 2, 0, 3]
result = join_and_sort_lists(list1, list2)
print(result)


# exercise 7
# create a list of dictionary elements with fields name, surname, sex,
# dob and age
# sort the list by name (alphabetical order)
# and then create a sublist of all the females

person_list = [
    {'name': 'Alice', 'surname': 'Smith', 'sex': 'female', 'dob': '1995-03-15', 'age': 28},
    {'name': 'Bob', 'surname': 'Johnson', 'sex': 'male', 'dob': '1990-08-20', 'age': 33},
    {'name': 'Eve', 'surname': 'Davis', 'sex': 'female', 'dob': '1992-12-05', 'age': 31}
]


def create_and_sort_person_list(a_person_list):
    sorted_person_list = sorted(a_person_list, key=lambda x: x['name'])
    females = [person for person in sorted_person_list if person['sex'] == 'female']
    return females


result = create_and_sort_person_list(person_list)
print(result)


# exercise 8
# Create a set of colours (red, white, etc.)
# a. add one colour (e.g. black)
# b. remove one colour
# c. check for presence of "white"
colors = {"red", "white", "blue"}


def manipulate_color_set():
    global colors
    # a. Add a color (black)
    colors.add("black")

    # b. Remove a color
    colors.discard("white")

    # c. Check for the presence of "white"
    is_white_present_loc = "white" in colors

    return colors, is_white_present_loc


result, is_white_present = manipulate_color_set()
print(result)
print(f"Is 'white' present? {is_white_present}")
