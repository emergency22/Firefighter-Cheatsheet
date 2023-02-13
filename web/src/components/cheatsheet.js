import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';

/**
 * The editHoses component for the website.
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
    }

    async displayCheatsheet() {
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";

        const apparatusList = await this.client.getApparatus(); 

        if (apparatusList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No apparatus exist for this account. Go back to add Apparatus.";
        }

        var thisIndividualApparatus = "thisIndividualApparatus";
        var thisHoseList = "thisHoseList";

        for (var i=0; i < apparatusList.length; i++) {

            console.log("apparatusList: " , apparatusList);
            
            let iString = i.toString();
            thisIndividualApparatus += iString;
            thisHoseList += iString;



            var currentApparatus = apparatusList[i];
            if (currentApparatus.fireDept != null) {
                var fireDept = currentApparatus.fireDept;
                var apparatusTypeAndNumber = currentApparatus.apparatusTypeAndNumber;
                var hoseList = currentApparatus.hoseList;

                document.getElementById('theDisplayArea').innerHTML += 
                `<h2><div class='individualApparatus' id='${thisIndividualApparatus}'> ${fireDept } ${apparatusTypeAndNumber} </h2><div id='${thisHoseList}'></div></div>`;
                console.log("thisIndividualApparatus: " , thisIndividualApparatus);
                for (var j=0; j < hoseList.length; j++) {
                    
                    var currentHose = hoseList[j];
                    if (currentHose != null) {
                        var name = currentHose.name;
                        var color = currentHose.color;
                        var pumpDischargePressureFog = currentHose.pumpDischargePressure;
                        var pumpDischargePressureSmoothBore = currentHose.pumpDischargePressure - 50;

                        var apparatusHoseInfo = 
                        "<li>" + color +
                        " " +
                        name +
                        "<br>" +
                        pumpDischargePressureFog +
                        " PSI (Fog Nozzle)<br>" + 
                        pumpDischargePressureSmoothBore +
                        " PSI (Smooth Bore)</li>";

                        document.getElementById(thisHoseList).innerHTML += apparatusHoseInfo;
                    }
                }
                thisHoseList = "thisHoseList";  //reset variable for next loop

            }
            
            thisIndividualApparatus = "thisIndividualApparatus"; //reset variable for next loop
            document.getElementById('addApparatusForm').innerHTML = "";
        }

    }
}