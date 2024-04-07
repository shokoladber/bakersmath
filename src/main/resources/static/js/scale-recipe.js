function scaleRecipe() {
    const recipeName = document.getElementById("recipeName").value;
    const scalingMethod = document.getElementById("scalingMethod").value;
    const scalingValue = document.getElementById("scalingValue").value;

    console.log(recipeName);
    console.log(scalingMethod);
    console.log(typeof scalingMethod);
    console.log(scalingValue);

    // Construct the URL based on the selected scaling method and value
    let url = '/recipes/scale?recipeName=' + recipeName + '&scalingMethod=' + scalingMethod;

    if (scalingMethod === 'BY_BATCH_SIZE') {
        url += '&batchSizeMultiplier=' + scalingValue;
    } else if (scalingMethod === 'BY_TOTAL_WEIGHT') {
        url += '&desiredTotalWeight=' + scalingValue + '&targetUnit=GRAMS'; // Assuming target unit is grams
    }

    fetch(url)
        .then(response => {
            // Return the JSON promise for the next `.then()` in the chain
            return response.json();
        })
        .then(data => {
            console.log(data);
            // Handle the scaled recipe data
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
