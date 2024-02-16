function updateIngredientQuantity(selectElement) {
    const targetUnitType = selectElement.value;
    const originalQuantity = selectElement.closest('li').querySelector('.ingredient-quantity').innerText;
    const currentUnitTypeElement = selectElement.closest('li').querySelector('.current-unit-type');
    const currentUnitType = currentUnitTypeElement.value;

    // Make an AJAX call to the server to fetch the converted quantity
    fetch('/recipes/convert-weight?unitType=' + currentUnitType + '&targetUnitType=' + targetUnitType + '&weight=' + originalQuantity)
        .then(response => response.json())
        .then(data => {
            console.log(data)
            // Update ingredient quantity
            const ingredientQuantityElement = selectElement.closest('li').querySelector('.ingredient-quantity');
            console.log(ingredientQuantityElement);
            if (ingredientQuantityElement) {
                ingredientQuantityElement.innerText = data.convertedWeight.toFixed(1);

                 // Update the value of the hidden current-unit-type input after fetch
                 currentUnitTypeElement.value = targetUnitType;
            }
        });
}
