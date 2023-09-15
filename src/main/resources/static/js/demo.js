"use strict";

var workflowId = "";
var documentId = "";
var copUrl = "";
var nw = null
//var resultObj = null;
const fileInput = document.getElementById('fileinput');

//Postmessage event listener
window.addEventListener('message', function (e) {
    console.log(e);
    if (e.origin === 'https://sgs-wm-test01.sunnystamp.com' && e.data === 'closed') {
        document.getElementById("result").innerHTML = "";  
        //nw.close();      
    }
});

const dispJson = (object) => {
    const sobject = JSON.stringify(object, undefined, 2);
    document.getElementById("result").innerHTML = "";
    document.getElementById("result").innerHTML = `<div style="align-self:center;max-height:400px;width:100%;border:1px solid;overflow-y:scroll"><pre>${sobject}</pre></div>`; 
}

const dispIframe = (src) => {
    document.getElementById("result").innerHTML = "";
    document.getElementById("result").innerHTML = `<iframe id="viewer" width="100%" height="800" src="${src}"></iframe>`;
}

const redirect = (url) => {
    //nw = window.open(url, '_blank', "popup,width=800,height=600");
    nw = window.open(url, '_blank');
}

const fetchAndProcess = (pUrl, pMethod, pBody, pHeaders, pCallback) => {
    const options = { method : pMethod };
    if (pBody) options.body = pBody;
    if (pHeaders) options.headers = pHeaders;

    fetch(pUrl, options)
    .then((response) => response.json())
    .then((object) => {
       pCallback(object);
    })
    .catch((error) => { alert(error); });
}

//"consentPageId" : "cop_DBbAf6y9rJwmQKmnW9mcSGsG", // OTP SMS
const buildWf = (rnd) => {
    return `{"name" : "API Demo #${rnd}",
        "steps" : [ {
            "stepType" : "signature",
            "recipients" : [ {
                "consentPageId" : "cop_Kz2LSvU5jk4mmXShhvPh79EL",
                "email" : "dschoenfel@gmail.com",
                "phoneNumber" : "+33 6 62 37 06 19",
                "firstName" : "Dominique",
                "lastName" : "Schoen",
                "country" : "FR",
                "preferredLocale" : "fr" 
            } ],
            "maxInvites" : 0
        } ]
    }`;
}

const createWf = () => {
    const workflow = buildWf(Math.floor(Math.random() * 100));
    fetchAndProcess('/createworkflow', 'POST', workflow, { "Content-Type": "application/json" }, (object) => {
        workflowId = object.id;
        dispJson(object);
    });
}

//Add document to the workflow
fileInput.onchange = () => {
    let formData = new FormData();
    formData.append('document', fileInput.files[0]);
    const selectedFile = fileInput.files[0];
    fetchAndProcess(`/adddocument?wfid=${workflowId}`, 'POST', formData, null, (object) => {
        documentId = object.documents[0].id;
        dispJson(object);
    });
}

const createViewer = () => {
    fetchAndProcess(`/createviewer?docid=${documentId}`, 'GET', null, null, (object) => {
        dispJson(object);
        dispIframe(object.viewerUrl);
    });
}

const startWorkflow = () => {
    fetchAndProcess(`/startworkflow?wfid=${workflowId}`, 'GET', null, null, dispJson);
}

// const createSignatureLink = () => {
//     fetchAndProcess(`/createsignaturelink?wfid=${workflowId}`, 'GET', null, null, (object) => {
//         redirect(object.inviteUrl)
//         //redirect(object.consentPageUrl);
//     });
// }

const createSignatureLink = () => {
    fetchAndProcess(`/createsignaturelink?wfid=${workflowId}`, 'GET', null, null, (object) => {
        dispIframe(object.inviteUrl); 
    });
}


const checkStatus = () => {
    fetchAndProcess(`/checkwfstatus?wfid=${workflowId}`, 'GET', null, null, dispJson);
}

const downloadDocs = () => {
    //workflowId = "wfl_CbiSunyQruW63DxR3FAHyJv3";
    dispIframe(`http://localhost:8080/downloaddoc?wfid=${workflowId}`);
    //fetchAndProcess(`/downloaddoc?wfid=${workflowId}`, 'GET', null, null, (object) => dispIframe(object));
}



