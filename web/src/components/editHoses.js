import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';

/**
 * The header component for the website.
 */
export default class EditHoses {
    constructor() {
        // super();

        // const methodsToBind = [
        //     'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
        //     'createLoginButton', 'createLoginButton', 'createLogoutButton', 'createUserInterface'
        // ];
        // this.bindClassMethods(methodsToBind, this);

        this.client = new FirefighterCheatsheetClient();
        // this.editHoses = new EditHoses();
    }

    /**
     * Display hoses on the page.
     */
    async displayHoses(fireDept, apparatusTypeAndNumber) {

        const apparatus = await this.client.getIndividualApparatus(fireDept, apparatusTypeAndNumber);
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";
        console.log("apparatus: " + apparatus);

        if (apparatus.hoseList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No hoses exist for this apparatus. Add a hose below."
        }

        document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus' id='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "</div>";

        const hoseList = apparatus.hoseList;
        console.log(hoseList.length);

        var currentHoseLocation = "currentHoseLocation";
        var currentHoseLocations = [];
        // var currentHoses = [];


        for (var i=0; i < hoseList.length; i++) {

            let iString = i.toString();
            currentHoseLocation += iString;
            // currentHose += iString;

            var currentHose = hoseList[i];
            if (currentHose != null) {
                var name = currentHose.name;
                var color = currentHose.color;
                var length = currentHose.length;
                var hoseDiameter = currentHose.hoseDiameter;
                var waterQuantityInGallons = currentHose.waterQuantityInGallons;
                var pumpDischargePressure = currentHose.pumpDischargePressure;
                console.log(name);
                console.log(color);
                console.log(length);
                console.log(hoseDiameter);
                console.log(waterQuantityInGallons);
                console.log(pumpDischargePressure);


                var apparatusHoseInfo = 
                "<li>" + color + 
                " " + 
                name + 
                " - " + 
                length +
                " Feet, " + 
                hoseDiameter +
                " Diameter, " + 
                waterQuantityInGallons +
                " Gallons, " + 
                pumpDischargePressure +
                " PSI " +
                "<span>" +
                    `<div class='delButton' id='${currentHoseLocation}'>X</div>` +
                `</span><div class='editHoses' id='${currentHose}'>` +
                `Calculate pump discharge pressure for ${color} ${name}` + "</div></li>";
                document.getElementById('theDisplayArea').innerHTML += apparatusHoseInfo;
                currentHoseLocations.push(currentHoseLocation);
                // currentHoses.push(currentHose);

                currentHoseLocation = "currentLocation";   //reset variable for the next loop
                // currentHose = "currentHose";  //reset variable for the next loop;
            }
        }
        for (var i=0; i < hoseList.length; i++) {
            var currentHoseLocation = currentHoseLocations[i];
        //     var currentHose = currentHoses[i];
        //     var fireDept = hoseList[i].fireDept;
            var hoseIndexNumber = i;

            this.createDeleteHoseButton(currentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
        //     this.createCalculatePDPButton(currentHose, fireDept, apparatusTypeAndNumber);
        // }
    }
}

    createDeleteHoseButton(currentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
        const button = document.getElementById(currentHoseLocation);
        button.classList.add('button');
        button.classList.add(currentHoseLocation);

        button.addEventListener('click', async () => {
            if (confirm("Click OK to delete this hose.") == true) {
            await this.client.deleteHose(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
            await this.displayHoses();  //reload the page
            }
        });
        return button;
    }

    // displayAddApparatusMenu() {
    //     (document.getElementById('addForm').innerHTML += "<form class='addAppForm' id='addAppForm'>" +
    //         "<label for='fireDept'>Add an apparatus: </label>" +
    //         "<input type='text' id='fireDept' placeHolder='Fire Department' style='width: 200px' required>" +
    //         "<input type='text' id='apparatusTypeAndNumber' placeHolder='Apparatus Type and Number' style='width: 200px' required>" +
    //         "<input type='submit' value='Add Apparatus'></div>"
    //     );
    //      this.addApparatusFormSubmitter();
    // }

    // addApparatusFormSubmitter() {
    //     var addApparatusForm = document.getElementById('addAppForm');
    //     addApparatusForm.addEventListener('submit', async (event) => {
    //         event.preventDefault()  //prevents auto-submit

    //         var inputFireDept = document.getElementById('fireDept').value;
    //         var inputApparatusTypeAndNumber = document.getElementById('apparatusTypeAndNumber').value;

    //         console.log("yup");
    //         await this.client.addHose(inputFireDept, inputApparatusTypeAndNumber);
    //         console.log("uh huh");

    //         await this.displayApparatus();
    //     });
    // }


}