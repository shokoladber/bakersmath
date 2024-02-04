
function removeIngredient(button) {
    let ingredientDiv = button.closest('.ingredient');

    if (ingredientDiv) {
        ingredientDiv.remove();
    }
}

function addIngredient() {
    let ingredientsContainer = document.getElementById('ingredientsContainer');

    let newIngredientDiv = document.createElement('div');
    newIngredientDiv.className = 'ingredient';

    let ingredientIndex = ingredientsContainer.getElementsByClassName('ingredient').length;

    let inputName = createInputElement('text', 'ingredients[' + ingredientIndex + '].name', 'Ingredient Name', true);
    let inputQuantity = createInputElement('number', 'ingredients[' + ingredientIndex + '].quantity', 'Quantity', true);
    let selectUnit = createSelectElement('ingredients[' + ingredientIndex + '].unit', 'Unit', true, ['GRAMS', 'OUNCES', 'MILLILITERS', 'LITERS', 'CUPS', 'TEASPOONS', 'TABLESPOONS', 'PINTS', 'QUARTS', 'GALLONS', 'POUNDS']);
    let removeButton = createButton('button', 'Remove Ingredient', function () {
        removeIngredient(this);
    });

    newIngredientDiv.appendChild(inputName);
    newIngredientDiv.appendChild(inputQuantity);
    newIngredientDiv.appendChild(selectUnit);
    newIngredientDiv.appendChild(removeButton);

    ingredientsContainer.appendChild(newIngredientDiv);

    // Clear input values for the next ingredient
    inputName.value = '';
    inputQuantity.value = '';
    selectUnit.value = '';

    ingredientIndex++;
}

function createInputElement(type, name, placeholder, required) {
    let inputElement = document.createElement('input');
    inputElement.type = type;
    inputElement.name = name;
    inputElement.placeholder = placeholder;
    inputElement.required = required;
    return inputElement;
}

function createSelectElement(name, placeholder, required, options) {
    let selectElement = document.createElement('select');
    selectElement.name = name;
    selectElement.required = required;

    let defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.text = placeholder;
    selectElement.add(defaultOption);

    for (let optionValue of options) {
        let option = document.createElement('option');
        option.value = optionValue;
        option.text = optionValue;
        selectElement.add(option);
    }

    return selectElement;
}


function createButton(type, textContent, clickHandler) {
    let button = document.createElement('button');
    button.type = type;
    button.textContent = textContent;
    button.onclick = clickHandler;
    return button;
}

    