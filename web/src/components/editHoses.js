import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';

/**
 * The editHoses component for the website.
 */
export default class EditHoses {
    constructor() {

        this.client = new FirefighterCheatsheetClient();
    }

    /**
     * Display hoses on the page.
     */
    async displayHoses(fireDept, apparatusTypeAndNumber) {

        const apparatus = await this.client.getIndividualApparatus(fireDept, apparatusTypeAndNumber);
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";
        document.getElementById('addHoseFormMain').innerHTML = "";

        if (apparatus.hoseList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No hoses exist for this apparatus. Add a hose below."
        }

        document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus' id='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "</div>";

        const hoseList = apparatus.hoseList;

        var currentHoseLocation = "currentHoseLocation";
        // var currentHoseId = "currentHoseId";
        var currentHoseLocations = [];
        // var currentHoseIds = [];

        for (var i=0; i < hoseList.length; i++) {

            let iString = i.toString();
            currentHoseLocation += iString;
            // currentHoseId += iString;

            var currentHose = hoseList[i];
            if (currentHose != null) {
                var name = currentHose.name;
                var color = currentHose.color;
                var length = currentHose.length;
                var hoseDiameter = currentHose.hoseDiameter;
                var waterQuantityInGallons = currentHose.waterQuantityInGallons;
                var pumpDischargePressure = currentHose.pumpDischargePressure;

                var apparatusHoseInfo = 
                "<li><u>" + color + 
                " " + 
                name + 
                "</u> - " + 
                length +
                " Feet, " + 
                hoseDiameter +
                '" Diameter, ' + 
                waterQuantityInGallons +
                " GPM " +
                // " GPM, " + 
                // pumpDischargePressure +
                // " PSI" +
                "<span>" +
                    `<div class='delButton' id='${currentHoseLocation}'>X</div>` +
                // `</span><div class='editHoses' id='${currentHoseId}'>` +
                `</span><div class='editHoses'>` +

                // `Calculate pump discharge pressure for ${color} ${name}` + "</div></li>";
                `${pumpDischargePressure} PSI on Fog Nozzle` + "</div></li>";


                document.getElementById('theDisplayArea').innerHTML += apparatusHoseInfo;
                
                currentHoseLocations.push(currentHoseLocation);
                // currentHoseIds.push(currentHoseId);

                currentHoseLocation = "currentLocation";   //reset variable for the next loop
                // currentHoseId = "currentHoseId";  //reset variable for the next loop;
            }
        }
        for (var i=0; i < hoseList.length; i++) {
            var thisCurrentHoseLocation = currentHoseLocations[i];
            // var thisCurrentHose = currentHoseIds[i];
            var hoseIndexNumber = i;

            this.createDeleteHoseButton(thisCurrentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
            // this.createCalculatePSIButton(thisCurrentHose, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
    }
    this.displayAddHoseMenu(fireDept, apparatusTypeAndNumber);
}

    createDeleteHoseButton(thisCurrentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
        const button = document.getElementById(thisCurrentHoseLocation);
        button.classList.add('button');
        button.classList.add(thisCurrentHoseLocation);

        button.addEventListener('click', async () => {
            if (confirm("Click OK to delete this hose.") == true) {
            await this.client.deleteHose(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
            await this.displayHoses(fireDept, apparatusTypeAndNumber);  //reload the page
            }
        });
        return button;
    }

    displayAddHoseMenu(fireDept, apparatusTypeAndNumber) {
        (document.getElementById('addHoseFormMain').innerHTML += "<form class='addHoseForm' id='addHoseForm'>" +
            "<label for='fireDept'>Add a hose: </label>" +
            "<input type='text' id='name' placeHolder='Name of Hose' style='width: 200px' required>" +

            `       
            <select id='colorSelector' name='colorSelector' required>
                <option value="">Color</option>
                <option value="White">White</option>
                <option value="Green">Green</option>
                <option value="Red">Red</option>
                <option value="Yellow">Yellow</option>
                <option value="Orange">Orange</option>
                <option value="Blue">Blue</option>
                <option value="Purple">Purple</option>
                <option value="Brown">Brown</option>
                <option value="Black">Black</option>
            </select>
           ` +

            `       
            <select id='lengthSelector' name='lengthSelector' required>
                <option value="">Length in Feet</option>
                <option value="50">50 Feet</option>
                <option value="100">100 Feet</option>
                <option value="150">150 Feet</option>
                <option value="200">200 Feet</option>
                <option value="250">250 Feet</option>
                <option value="300">300 Feet</option>
                <option value="350">350 Feet</option>
                <option value="400">400 Feet</option>
                <option value="450">450 Feet</option>
                <option value="500">500 Feet</option>
            </select>
            ` +

            `
            <select id='diameterSelector' name='diameterSelector' required>
                <option value="">Hose Diameter</option>
                <option value="1.5">1.5 Inches</option>
                <option value="1.75">1.75 Inches</option>
                <option value="2">2 Inches</option>
                <option value="2.5">2.5 Inches</option>
                <option value="3">3 Inches</option>
            </select>
            ` +

            `       
            <select id='gpmSelector' name='gpmSelector' required>
                <option value="">Gallons Per Minute</option>
                <option value="50">50 GPM</option>
                <option value="100">100 GPM</option>
                <option value="150">150 GPM</option>
                <option value="200">200 GPM</option>
                <option value="250">250 GPM</option>
                <option value="300">300 GPM</option>
                <option value="350">350 GPM</option>
                <option value="400">400 GPM</option>
                <option value="450">450 GPM</option>
                <option value="500">500 GPM</option>
            </select>
            ` +

            "<input type='submit' style='font-weight:bold' value='Add Hose'></form>"
        );
            this.addHoseFormSubmitter(fireDept, apparatusTypeAndNumber);
    }

    addHoseFormSubmitter(fireDept, apparatusTypeAndNumber) {
        var addHoseFormToClient = document.getElementById('addHoseForm');
        addHoseFormToClient.addEventListener('submit', async (event) => {
            event.preventDefault()  //prevents auto-submit

            var inputName = document.getElementById('name').value;
            var inputColor = document.getElementById('colorSelector').value;
            var inputLength = document.getElementById('lengthSelector').value;
            var inputDiameter = document.getElementById('diameterSelector').value;
            var inputGallons = document.getElementById('gpmSelector').value;
    
            await this.client.addHose(fireDept, apparatusTypeAndNumber, inputName, inputColor, inputLength, inputDiameter, inputGallons);
            await this.displayHoses(fireDept, apparatusTypeAndNumber);
        });
    }

    // createCalculatePSIButton(thisCurrentHose, fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
    //     const button = document.getElementById(thisCurrentHose);
    //     button.classList.add('button');
    //     button.classList.add(thisCurrentHose);

    //     button.addEventListener('click', async () => {
    //         if (confirm("Click OK to calculate a PSI for this hose.") == true) {
    //         await this.client.calculatePSI(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
    //         await this.displayHoses(fireDept, apparatusTypeAndNumber);  //reload the page
    //         }
    //     });
    //     return button;
    // }

    // async createCalculatePSIButton(thisCurrentHose, fireDept, apparatusTypeAndNumber, hoseIndexNumber) {

    //     //get the element to edit and give it a variable name
    //     //calculate the PSI and give that a variable name
    //     //add the PSI to the original variable name

    //     const fillPSI = document.getElementById(thisCurrentHose);

    //     var psi = await this.client.calculatePSI(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
    //     var actualPSI = psi.hoseList[0].pumpDischargePressure;
    //     fillPSI.innerHTML += actualPSI;

    //     // button.addEventListener('click', async () => {
    //     //     if (confirm("Click OK to calculate a PSI for this hose.") == true) {
    //     //     await this.client.calculatePSI(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
    //     //     await this.displayHoses(fireDept, apparatusTypeAndNumber);  //reload the page
    //     //     }
    //     // });
    //     // return button;
    // }


    // async calcPSI(fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
    //     const fillPSI = document.getElementById(thisCurrentHose);

    //     var psi = await this.client.calculatePSI(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
    //     var actualPSI = psi.hoseList[0].pumpDischargePressure;
    //     fillPSI.innerHTML += actualPSI;
    // }

}