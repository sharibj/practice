const AXIS_COLOR = 'gray';
const INNER_CIRCLE_COLOR = 'red';
const OUTER_CIRCLE_COLORS = ['blue', 'green'];

function getCanvas() {
    return document.getElementById("myCanvas");
}

function getCanvasContext() {
    return getCanvas().getContext("2d");
}

function getCanvasWidth() {
    return parseFloat(getCanvas().attributes['width'].value);
}

function getCanvasHeight() {
    return parseFloat(getCanvas().attributes['height'].value);
}

function fitToContainer() {
    let canvas = getCanvas();
    canvas.style.width = '100%';
    canvas.style.height = '100%';
    canvas.width = canvas.offsetWidth;
    canvas.height = canvas.offsetHeight;
}

function clearCanvas() {
    getCanvasContext().clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
}

function drawAxis() {
    let xSize = getCanvasWidth() / 2;
    let ySize = getCanvasHeight() / 2;
    // Draw x axis
    drawLine(-xSize, 0, xSize, 0, AXIS_COLOR);
    // Draw y axis
    drawLine(0, -ySize, 0, ySize, AXIS_COLOR);
}

function drawLine(x1, y1, x2, y2, color) {
    let ctx = getCanvasContext();
    ctx.beginPath();
    ctx.moveTo(translateX(x1), translateY(y1));
    ctx.lineTo(translateX(x2), translateY(y2));
    ctx.strokeStyle = color ? color : 'black';
    ctx.stroke();
}

function drawCircle(x, y, radius, showCenter, color) {
    let ctx = getCanvasContext();
    ctx.beginPath();
    ctx.arc(translateX(x), translateY(y), radius, 0, 2 * Math.PI);
    ctx.strokeStyle = color ? color : 'black';
    ctx.stroke();
    if (showCenter)
        drawCircle(x, y, 2, false, color);
}

function translateX(num) {
    return num + getCanvasWidth() / 2;
}

function translateY(num) {
    return num + getCanvasHeight() / 2;
}

/****************************************************************************************************/

window.onload = function () {
    fitToContainer();
    clearAll();
};

function clearAll() {
    clearCanvas();
    clearLogs();
    drawAxis();
}

function clearLogs() {
    document.getElementById("logs").innerText = "";
}

function appendLog(title, value) {
    document.getElementById("logs").innerHTML +=
        "<br><b>" + title + ":</b>" + value + "<br>";
}

function radianToDegree(rad) {
    return rad * (180 / Math.PI);
}

class Point {
    constructor(x, y, theta, r) {
        this.x = x;
        this.y = y;
        //theta = 0,360 -> x = 0, y = -r
        this.theta = theta;
        this.r = r;
    }

    toString() {
        return "[ x=" + this.x + ", y=" + this.y + ", θ=" + radianToDegree(this.theta) + ", r=" + this.r + " ]";
    }
}

function plotCirclesForPoints(points, showCenter) {
    points.forEach((point, index) =>
        drawCircle(point.x, point.y, point.r, showCenter, OUTER_CIRCLE_COLORS[index % OUTER_CIRCLE_COLORS.length]));
}

function logPoints(points) {
    points.forEach((point, index) =>
        appendLog("point[" + (index + 1) + "]", point.toString()));
}

/****************************************************************************************************/

function getSortedNumbersFromString(inputStr) {
    return inputStr.split(',')
        .filter(value => !isNaN(parseFloat(value)) && isFinite(value))
        .map(value => parseFloat(value))
        .sort((a, b) => a - b);
}

function getDiametersArray() {
    return getSortedNumbersFromString(document.getElementById("diameter_input").value);
}

function getRadiusArray(diametersArray) {
    return diametersArray.flatMap(value => [value / 2, value / 2]);
}

function getPolygonSidesArray(radiusArray) {
    let sides = [];
    sides[0] = radiusArray[0] + radiusArray[radiusArray.length - 1];
    for (let i = 1, j = 1; i < radiusArray.length - 1; i += 2, j++) {
        sides[j] = radiusArray[i] + radiusArray[i + 1];
    }
    return sides;
}

/****************************************************************************************************/

function getAngel(a, b) {
    // isosceles triangle equation
    return (Math.asin((a / (2 * b)))) * 2;
}

function getSumOfAngle(aArray, b) {
    return aArray.map(a => getAngel(a, b))
        .reduce((a, b) => a + b);
}

function getAllAngles(aArray, b) {
    return aArray.map(a => getAngel(a, b));
}

/****************************************************************************************************/

function isSmallRadius(aArray, radius) {
    return getSumOfAngle(aArray, radius) > 6.283199;
}

function isLargeRadius(aArray, radius) {
    return getSumOfAngle(aArray, radius) < 6.283190;
}

function increaseRadius(aArray, radius, step) {
    if (isSmallRadius(aArray, radius, step)) {
        return increaseRadius(aArray, radius + step, step);
    }
    return radius;
}

function decreaseRadius(aArray, radius, step) {
    if (isLargeRadius(aArray, radius, step)) {
        return decreaseRadius(aArray, radius - step, step);
    }
    return radius;
}

function adjustRadius(aArray, radius, step) {
    if (isSmallRadius(aArray, radius, step)) {
        radius = adjustRadius(aArray, increaseRadius(aArray, radius, step), step / 10);
    } else if (isLargeRadius(aArray, radius, step)) {
        radius = adjustRadius(aArray, decreaseRadius(aArray, radius, step), step / 10);
    }
    return radius;
}

function incrementallyAdjustRadius(aArray) {
    return adjustRadius(aArray, aArray[aArray.length - 1] / 2, 100);
}

/****************************************************************************************************/

function calculatePointOnCircumferenceOfInnerCircle(innerRadius, angle) {
    let x = innerRadius * Math.sin(angle);
    let y = innerRadius * Math.cos(angle);
    return {x, y};
}

function getPointForAngle(angle, innerRadius, radius) {
    let point = calculatePointOnCircumferenceOfInnerCircle(innerRadius, angle);
    return new Point(point.x, point.y, angle, radius);
}

function calculateCenterPointsForGivenAngles(innerRadius, angles, diameters) {
    return angles.map((_, index, allAngles) => {
            let angle = allAngles.slice(0, index + 1)
                .reduce((a, b) => a + b);
            return getPointForAngle(angle, innerRadius, diameters[index] / 2)
        }
    );
}

function handleEdgeCases(diameters, showCenter) {
    let radius;
    let points = [];
    if (diameters.length == 1) {
        radius = diameters[0] / 2;
        drawCircle(0, 0, radius, showCenter, INNER_CIRCLE_COLOR);
        points[0] = new Point(radius, 0, 0, radius);
    } else if (diameters.length == 2) {
        radius = ((diameters[0] / 2) + (diameters[1] / 2)) / 2;
        drawCircle(0, 0, radius, showCenter, INNER_CIRCLE_COLOR);
        points[0] = new Point(radius, 0, 0, diameters[0] / 2);
        points[1] = new Point(-radius, 0, 180, diameters[1] / 2);
    }
    appendLog("Inner Radius", radius);
    logPoints(points);
    plotCirclesForPoints(points, showCenter);
}

function onClickForCalculateButton(showCenter) {
    clearAll();
    let sideArray = getPolygonSidesArray(getRadiusArray(getDiametersArray()));
    if (sideArray.length < 3) {
        handleEdgeCases(getDiametersArray(), showCenter);
        return;
    }
    let radius = incrementallyAdjustRadius(sideArray);
    appendLog("Inner Radius", radius);
    drawCircle(0, 0, radius, showCenter, INNER_CIRCLE_COLOR);
    let angles = getAllAngles(sideArray, radius);
    let points = calculateCenterPointsForGivenAngles(radius, angles, getDiametersArray());
    logPoints(points);
    plotCirclesForPoints(points, showCenter);
}

/*
function calculatePointsForRadius(angles, innerRadius, radiusArray) {
    let points = [];
    let point = calculateCenterPointForCircle(innerRadius, angles[0]);
    points[0] = new Point(point.x, point.y, angles[0], radiusArray[0]);
    for (let i = 1, j = 2; j <= angles.length; i++, j += 2) {
        let theta = points[i - 1].theta + angles[j - 1] + angles[j];
        let point = calculateCenterPointForCircle(innerRadius, theta);
        points[i] = new Point(point.x, point.y, theta, radiusArray[j]);
    }
    return points;
}
function onClickForCalculateButton2() {
    let showCenter = true;
    clearAll();
    let radiusArray = getRadiusArray(getDiametersArray());
    let radius = incrementallyAdjustRadius(radiusArray);
    appendLog("Inner Radius", radius);
    drawCircle(0, 0, radius, showCenter);
    let angles = getAllAngles(radiusArray, radius);
    let points = calculatePointsForRadius(angles, radius, radiusArray);
    logPoints(points);
    plotCircles(points, showCenter);
}*/