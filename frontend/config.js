var mustache = require('mustache');
var fs = require('fs');

var view = {
  production: true,
  baseApiUrl: process.env.baseApiUrl ? process.env.baseApiUrl : 'http://localhost:8080/api'
};

var template = 
"export const environment = { \n" + 
"	production: {{{production}}}, \n" +
"	W3_API_URL: '{{{baseApiUrl}}}' \n" +
"}";


var output = mustache.render(template, view);
fs.writeFileSync('src/environments/environment.prod.ts', output);

view.production = false;
var outputProd = mustache.render(template, view);
fs.writeFileSync('src/environments/environment.ts', outputProd);