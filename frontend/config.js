var mustache = require('mustache');
var fs = require('fs');

var view = {
  production: true,
  baseUrl: process.env.baseUrl
};

var template = 
"export const environment = { \n" + 
"	production: {{{production}}}, \n" +
"	W3_API_URL: '{{{baseUrl}}}' \n" +
"}";


var output = mustache.render(template, view);
fs.writeFileSync('src/environments/environment.prod.ts', output);

view.production = false;
var outputProd = mustache.render(template, view);
fs.writeFileSync('src/environments/environment.ts', outputProd);