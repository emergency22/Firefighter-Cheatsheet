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
        console.log("editHoses: fireDept: " + fireDept);
        console.log("editHoses: apparatusTypeAndNumber: " + apparatusTypeAndNumber);

        const apparatus = await this.client.getIndividualApparatus(fireDept, apparatusTypeAndNumber);
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";
        console.log("apparatus: " + apparatus);

        if (apparatus == null) {
            document.getElementById('theDisplayArea').innerHTML = "No hoses exist for this apparatus. Add a hose below."
        }

        document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "</div>";



    }
}