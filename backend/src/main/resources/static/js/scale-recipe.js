function scaleRecipe(username) {
    const recipeName = document.getElementById("recipeName").value;
    const scalingMethod = document.getElementById("scalingMethod").value;
    const scalingValue = document.getElementById("scalingValue").value;

    console.log(recipeName);
    console.log(scalingMethod);
    console.log(typeof scalingMethod);
    console.log(scalingValue);
    console.log(username);

    // Construct the args array based on the scaling method and value
    let args = [];
    if (scalingMethod === 'BY_BATCH_SIZE') {
        args.push(parseFloat(scalingValue)); // Convert scalingValue to float
        args.push(username); // Add username
    } else if (scalingMethod === 'BY_TOTAL_WEIGHT') {
        args.push(parseFloat(scalingValue)); // Convert scalingValue to float
        args.push("GRAMS"); // Assuming target unit is grams
    }

    // Encode the args array properly
    const encodedArgs = encodeURIComponent(JSON.stringify(args));

    // Construct the URL with recipeName, scalingMethod, and encoded args
    let url = `/recipes/scale?recipeName=${recipeName}&scalingMethod=${scalingMethod}&args=${encodedArgs}`;

    console.log(url);

    // Fetch data from the URL
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Parse response as JSON and return the promise
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
