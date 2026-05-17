async function uploadPdf() {

    const fileInput = document.getElementById("pdfFile");
    const status = document.getElementById("uploadStatus");

    const file = fileInput.files[0];

    if (!file) {
        alert("Please select a PDF file");
        return;
    }

    status.innerText = "Uploading and processing PDF...";

    const formData = new FormData();
    formData.append("file", file);

    try {

        const response = await fetch("/pdf/upload", {
            method: "POST",
            body: formData
        });

        const data = await response.json();

        localStorage.setItem("sessionId", data.sessionId);

        status.innerText = "PDF processed successfully ✅";

    } catch (error) {

        status.innerText = "Error uploading PDF ❌";

    }
}


async function askQuestion(){

    const question = document.getElementById("question").value;
    const answerBox = document.getElementById("answer");
    const loading = document.getElementById("loadingStatus");

    const sessionId = localStorage.getItem("sessionId");

    if(!sessionId){
        alert("Please upload a PDF first.");
        return;
    }

    if(!question){
        alert("Please enter a question.");
        return;
    }

    loading.innerText = "Getting answer...";

    answerBox.innerText = "";

    try {

        const response = await fetch(
            `/chat?sessionId=${sessionId}&question=${encodeURIComponent(question)}`
        );

        const answer = await response.text();

        loading.innerText = "";

        answerBox.innerText = answer;

    } catch (error){

        loading.innerText = "";
        answerBox.innerText = "Error getting answer.";

    }
}

//async function uploadRepo() {
//
//    const repoUrl =
//        document.getElementById("repoUrl").value;
//
//    const repoStatus =
//        document.getElementById("repoStatus");
//
//    if (!repoUrl) {
//        alert("Please enter GitHub repository URL");
//        return;
//    }
//
//    repoStatus.innerText =
//        "Cloning and processing repository...";
//
//    try {
//
//        const response = await fetch(
//            "/repo/upload",
//            {
//                method: "POST",
//                headers: {
//                    "Content-Type": "application/json"
//                },
//                body: JSON.stringify({
//                    repoUrl: repoUrl,
//                    sessionId: localStorage.getItem("sessionId")
//                })
//            }
//        );
//
//        const data = await response.text();
//
//        repoStatus.innerText = data + " ✅";
//
//    } catch (error) {
//
//        repoStatus.innerText =
//            "Error processing repository ❌";
//
//    }
//}

async function uploadRepo() {

    const repoUrl =
        document.getElementById("repoUrl").value;

    const repoStatus =
        document.getElementById("repoStatus");

    if (!repoUrl) {

        alert("Please enter GitHub repository URL");

        return;
    }

    let sessionId =
        localStorage.getItem("sessionId");

    if (!sessionId) {

        sessionId =
            "repo-" + Date.now();

        localStorage.setItem(
            "sessionId",
            sessionId
        );
    }

    repoStatus.innerText =
        "Processing repository...";

    try {

        const response = await fetch(
            "/repo/upload",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    repoUrl: repoUrl,
                    sessionId: sessionId
                })
            }
        );

        const data = await response.text();

        repoStatus.innerText =
            data + " ✅";

    } catch (error) {

        console.error(error);

        repoStatus.innerText =
            "Error processing repository ❌";
    }
}