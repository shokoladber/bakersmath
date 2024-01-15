<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Baker's Assistant</title>
</head>
<body>

<header>
    <h1><%= request.getAttribute("homepageTitle") %></h1>
</header>

<section>
    <h2>Explore Our Recipes</h2>
    <p>Discover a variety of delicious recipes to try in your kitchen.</p>
    <a href="/Recipes/index-recipes" class="btn">View Recipes</a>
</section>

<section>
    <h2></h2>
    <p>
        Bakers Assistant is your go-to platform for finding and exploring baking recipes.
        Whether youre a seasoned baker or just starting, we have something for everyone.
    </p>
</section>

<footer>
    <p>&copy; 2023 Baker's Assistant. All rights reserved.</p>
</footer>

</body>
</html>
