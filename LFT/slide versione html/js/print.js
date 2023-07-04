const puppeteer = require('puppeteer-core');
// const puppeteer = require('puppeteer');

(async () => {
    const input = process.argv[2];
    const output = process.argv[3];
    const format = process.argv[4];
    const orientation = process.argv[5];
    const browser = await puppeteer.launch({executablePath: '/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome'});
    // const browser = await puppeteer.launch();
    const page = await browser.newPage();
    await page.goto(input, {waitUntil: 'networkidle2'});
    await page.pdf({path: output,
		    landscape: orientation == "landscape",
		    preferCSSPageSize: false,
		    scale: 1,
		    printBackground: true,
		    format: format});
    await browser.close();
})();
