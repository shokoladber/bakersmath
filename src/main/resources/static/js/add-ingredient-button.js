let ingredientIndex = 1;

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

    let inputName = createInputElement('text', 'ingredients[' + ingredientIndex + '].name', 'Ingredient Name', true);
    let inputQuantity = createInputElement('number', 'ingredients[' + ingredientIndex + '].quantity', 'Quantity', true);
    let inputUnit = createInputElement('text', 'ingredients[' + ingredientIndex + '].unit', 'Unit', true);

    let removeButton = createButton('button', 'Remove Ingredient', function () {
        removeIngredient(this);
    });

    newIngredientDiv.appendChild(inputName);
    newIngredientDiv.appendChild(inputQuantity);
    newIngredientDiv.appendChild(inputUnit);
    newIngredientDiv.appendChild(removeButton);

    ingredientsContainer.appendChild(newIngredientDiv);

    // Clear input values for the next ingredient
    inputName.value = '';
    inputQuantity.value = '';
    inputUnit.value = '';

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

function createButton(type, textContent, clickHandler) {
    let button = document.createElement('button');
    button.type = type;
    button.textContent = textContent;
    button.onclick = clickHandler;
    return button;
}

    