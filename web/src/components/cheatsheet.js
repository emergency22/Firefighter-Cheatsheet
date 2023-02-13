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

        //within theDisplayArea, for each apparatus, assign an apparatus display area. 
        //display the apparatus fireDept and type/num first. Then assign a hoseList area.
        //Then display each hose within the hoseList area.
        //for each hose, display the Color, Name, and PSI in that order.

        var thisIndividualApparatus = "thisIndividualApparatus";
        var thisHoseList = "thisHoseList";
        // var individualApparatusArray = [];
        // var thisHoseListArray = [];

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
                `<div class='individualApparatus' id='${thisIndividualApparatus}'> ${fireDept } ${apparatusTypeAndNumber} <div id='${thisHoseList}'></div></div>`;
                console.log("thisIndividualApparatus: " , thisIndividualApparatus);
                for (var j=0; j < hoseList.length; j++) {
                    
                    var currentHose = hoseList[j];
                    if (currentHose != null) {
                        var name = currentHose.name;
                        var color = currentHose.color;
                        var pumpDischargePressure = currentHose.pumpDischargePressure;

                        var apparatusHoseInfo = 
                        "<li>" + color +
                        " " +
                        name +
                        " - " +
                        pumpDischargePressure +
                        " PSI </li>";

                        document.getElementById(thisHoseList).innerHTML += apparatusHoseInfo;
                    }
                    // thisHoseListArray.push(thisHoseList);
                }
                thisHoseList = "thisHoseList";  //reset variable for next loop

                // individualApparatusArray.push(thisIndividualApparatus);
            }
            
            thisIndividualApparatus = "thisIndividualApparatus"; //reset variable for next loop

        }

    }
}