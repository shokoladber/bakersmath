function updateBatchSize() {
    const batchSizeMultiplier = document.getElementById("batchSize").value;
    const recipeName = document.getElementById("recipeName").value

    console.log(batchSizeMultiplier);
    console.log(recipeName);

    fetch('/recipes/scale?recipeName=' + recipeName + '&batchSizeMultiplier=' + batchSizeMultiplier)
        .then(response => {
        console.log(response.json());
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
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