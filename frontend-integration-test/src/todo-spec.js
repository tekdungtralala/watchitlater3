describe('home page', function() {
  it('should get correct title', function() {

    browser.driver.get('http://host.docker.internal:8090');
    browser.driver.getTitle().then(function(webpagetitle){
        expect(webpagetitle).toEqual('Watch it later');
    });
  });
});
