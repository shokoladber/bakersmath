let ingredientIndex = 1;

function removeIngredient(button) {
    // Get the specific div containing the ingredient fields
    let ingredientDiv = button.closest('.ingredient');

    // Check if the ingredientDiv is found
    if (ingredientDiv) {
        // Remove the ingredientDiv from the container
        ingredientDiv.remove();
    }
}


    function addIngredient() {
        let ingredientsContainer = document.getElementById('ingredientsContainer');

        let newIngredientDiv = document.createElement('div');
        newIngredientDiv.className = 'ingredient';

        let inputName = document.createElement('input');
        inputName.type = 'text';
        inputName.name = 'ingredients[' + ingredientIndex + '].name';
        inputName.placeholder = 'Ingredient Name';
        inputName.required = true;

        let inputQuantity = document.createElement('input');
        inputQuantity.type = 'number';
        inputQuantity.name = 'ingredients[' + ingredientIndex + '].quantity';
        inputQuantity.placeholder = 'Quantity';
        inputQuantity.required = true;

        let inputUnit = document.createElement('input');
        inputUnit.type = 'text';
        inputUnit.name = 'ingredients[' + ingredientIndex + '].unit';
        inputUnit.placeholder = 'Unit';
        inputUnit.required = true;

        let addButton = document.createElement('button');
        addButton.type = 'button';
        addButton.textContent = 'Add Ingredient';
        addButton.onclick = function () {
            addIngredient();
        };

        let removeButton = document.createElement('button');
        removeButton.type = 'button';
        removeButton.textContent = 'Remove Ingredient';
        removeButton.onclick = function () {
            removeIngredient(this);
        };

        newIngredientDiv.appendChild(inputName);
        newIngredientDiv.appendChild(inputQuantity);
        newIngredientDiv.appendChild(inputUnit);
        newIngredientDiv.appendChild(addButton);
        newIngredientDiv.appendChild(removeButton);

        ingredientsContainer.appendChild(newIngredientDiv);

        ingredientIndex++;
    }
    