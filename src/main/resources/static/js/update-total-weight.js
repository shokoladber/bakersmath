    // Function to update total weight based on the selected unit type
    function updateTotalWeight() {
        const targetUnitTypeElement = document.getElementById("targetUnitType");
        const targetUnitType = targetUnitTypeElement.options[targetUnitTypeElement.selectedIndex].value;
        const unitTypeElement = document.getElementById("currentUnitType");
        const unitType = unitTypeElement.innerText;
        const totalWeight = parseFloat(document.getElementById("totalWeight").innerText);

        // Update the currentUnitType
        document.getElementById("currentUnitType").innerText = targetUnitType;

        // Make an AJAX call to the server to fetch the converted total weight
        fetch('/recipes/convert-weight?unitType=' + unitType + '&targetUnitType=' + targetUnitType + '&weight=' + totalWeight)
            .then(response => response.json())
            .then(data => {
                document.getElementById("totalWeight").innerText = data.convertedWeight;
            });
    }
    // Initial call to set default total weight and unit type
    updateTotalWeight();
