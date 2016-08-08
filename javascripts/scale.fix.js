var metas = document.getElementsByTagName('meta');
var i;
if (navigator.userAgent.match(/iPhone/i)) {
  for (i=0; i<metas.length; i++) {
    if (metas[i].name == "viewport") {
      metas[i].content = "width=device-width, minimum-scale=1.0, maximum-scale=1.0";
    }
  }
  document.addEventListener("gesturestart", gestureStart, false);
}
function gestureStart() {
  for (i=0; i<metas.length; i++) {
    if (metas[i].name == "viewport") {
      metas[i].content = "width=device-width, minimum-scale=0.25, maximum-scale=1.6";
    }
  }
}
var url = 'https://api.github.com/repos/Pishtiko/java_in_examples/git/trees/master?recursive=true'
//var url = 'https://api.github.com/repos/akvelon/SmartsheetTests/git/trees/develop?recursive=true'

function printRepoCount() {
  var responseObj = JSON.parse(this.responseText);
  console.log(responseObj.name + " has " + JSON.stringify(responseObj) + " public repositories!");
}
var request = new XMLHttpRequest();
request.onload = printRepoCount;
request.open('get', url, true)
request.send()
