import "bootstrap/dist/css/bootstrap.min.css";
import { ChevronLeft, ChevronRight, Star } from "lucide-react";
import Slider from "@mui/material/Slider";
import { Modal, Box, Typography } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { useEffect } from "react";
import "../styles/UserDemandeStyles.css";
import axios from "axios";

// import Image from 'next/image'
const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

export default function WorkerListing() {
  const [rating, setRating] = useState(0);

  // useEffect(() => {
  //   console.log(rating);

  // }, [rating]);

  const valuetext = (value) => {
    setRating(value);
  };
  // const [hover, setHover] = useState(0);
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [evaluationWorker, setEvaluationWorker] = useState(false);
  const [noAgreementWorker, setNoAgreementWorker] = useState(false);
  const [userDemandesEvaluated, setUserDemandesEvaluated] = useState([]);
  const [userDemandesNotEvaluated, setUserDemandesNotEvaluated] = useState([]);
  const [userDemande,setUserDemandes] = useState([])

  
  useEffect(() => {
    axios
      .post("http://localhost:8080/demande/paginatedListByCriteria", {
        page: 0,
        maxResults: 1,
        sortOrder: "",
        sortField: "",
      })
      .then((response) => {
        setUserDemandes(response.data.list)
      })
      .catch((error) => {
        console.error("Erreur lors de la récupération des ouvriers :", error);
      });
  }, []);
  // Initialisation initiale
  useEffect(() => {
    const evaluatedDemandes = userDemande.filter(
      (demande) => demande.evaluation === true
    );
    const notEvaluatedDemandes = userDemande.filter(
      (demande) => demande.evaluation === false
    );

    setUserDemandesEvaluated(evaluatedDemandes);
    setUserDemandesNotEvaluated(notEvaluatedDemandes);
  }, []);

  const handleEvaluationWorker = () => {
    handleOpen();
    setEvaluationWorker(true);
  };

  const demandeToMoveFunction = (demandeId) => {
    // Trouver la demande dans la liste non évaluée
    const demandeToMove = userDemandesNotEvaluated.find(
      (demande) => demande.id === demandeId
    );
    if (demandeToMove) {
      //   // Modifier l'évaluation de la demande
      const updatedDemande = {
        ...demandeToMove,
        evaluation: true,
        agreement: true,
        rating: rating,
      };
      //   // Retirer la demande de la liste non évaluée
      setUserDemandesNotEvaluated((prev) =>
        prev.filter((demande) => demande.id !== demandeId)
      );
      //   // Ajouter la demande à la liste évaluée
      setUserDemandesEvaluated((prev) => [...prev, updatedDemande]);
    }

    handleClose();
  };

  const modifyEvaluationWorker = (demandeId) => {
    // Trouver la demande dans la liste évaluée
    const demandeToMove = userDemandesEvaluated.find(
      (demande) => demande.id === demandeId
    );

    if (demandeToMove) {
      // Modifier l'évaluation de la demande
      const updatedDemande = { ...demandeToMove, evaluation: false };

      // Retirer la demande de la liste évaluée
      setUserDemandesEvaluated((prev) =>
        prev.filter((demande) => demande.id !== demandeId)
      );

      // Ajouter la demande à la liste non évaluée
      setUserDemandesNotEvaluated((prev) => [...prev, updatedDemande]);
    }

    setEvaluationWorker(false);
    setNoAgreementWorker(false);
  };

  const noAgreement = (demandeId) => {
    setEvaluationWorker(false);
    setNoAgreementWorker(true);

    // Trouver la demande dans la liste non évaluée
    const demandeToMove = userDemandesNotEvaluated.find(
      (demande) => demande.id === demandeId
    );
    if (demandeToMove) {
      //   // Modifier l'évaluation de la demande
      const updatedDemande = {
        ...demandeToMove,
        evaluation: true,
        agreement: false,
      };
      //   // Retirer la demande de la liste non évaluée
      setUserDemandesNotEvaluated((prev) =>
        prev.filter((demande) => demande.id !== demandeId)
      );
      //   // Ajouter la demande à la liste évaluée
      setUserDemandesEvaluated((prev) => [...prev, updatedDemande]);
    }

    handleClose();
  };

  return (
    <div className="container py-4 w-100" style={{ direction: "rtl" }}>
      {/* Search Section */}
      <div className="d-flex gap-2 mb-4 row">
        <div className=" col-12 col-md-9">
          <input
            type="text"
            className="form-control w-100"
            placeholder="أدخل اسم العامل"
          />
        </div>

        <div className="col-12 col-md-2">
          <select class="form-select" aria-label="Default select example">
            <option selected>تم تقييمها</option>
            <option value=""></option>
            <option value="true">نعم</option>
            <option value="False">لا</option>
          </select>
        </div>
      </div>

      <div className="d-flex flex-column gap-3">
        {/* First Worker */}
        <div className="card">
          <div className="card-body">
            <h5 class="card-title d-flex justify-content-start">
              طلبيات قمت بها:{" "}
            </h5>
            {userDemandesNotEvaluated.map((userDemande) => (
              <div className="d-flex flex-column gap-3">
                {/* First Worker */}
                <div className="card">
                  <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center">
                      <div className="d-flex align-items-center gap-3">
                        <div
                          style={{
                            width: "48px",
                            height: "48px",
                            position: "relative",
                          }}
                        >
                          <img
                            src={userDemande.review.avatar}
                            alt={userDemande.review.name}
                            width={48}
                            height={48}
                            className="rounded-circle"
                          />
                        </div>
                        <div>
                          <h6 className="mb-0">{userDemande.review.name}</h6>
                          <small className="text-muted">
                            {userDemande.review.profession}
                          </small>
                        </div>
                      </div>

                      <div className="d-flex gap-3  row">
                        {evaluationWorker === false &&
                        noAgreementWorker === false ? (
                          <button
                            onClick={() => handleEvaluationWorker()}
                            className="btn btn-success btn-customm col-6"
                          >
                            تم العمل
                          </button>
                        ) : evaluationWorker === false &&
                          noAgreementWorker === true ? (
                          ""
                        ) : (
                          <div className="d-flex gap-1 col-9">
                            {[...Array(5)].map((_, index) => (
                              <Star
                                key={index}
                                size={20} // Augmenté de 20 à 40
                                className={`w-5 ${
                                  rating > index ? "text-warning" : "text-muted"
                                }`} // Augmenté w-5 à w-8 pour la cohérence
                                style={{
                                  fill: rating > index ? "#f39c12" : "none",
                                  stroke: rating > index ? "none" : "#ccc",
                                  strokeWidth: 2,
                                }}
                              />
                            ))}
                          </div>
                        )}
                        <Modal
                          open={open}
                          onClose={handleClose}
                          aria-labelledby="modal-modal-title"
                          aria-describedby="modal-modal-description"
                        >
                          <Box sx={style}>
                            <Typography
                              id="modal-modal-title"
                              variant="h6"
                              component="h2"
                            >
                              ما هو تقييمك لهدا العمل؟
                            </Typography>
                            <Typography
                              id="modal-modal-description"
                              sx={{ mt: 2 }}
                            >
                              <Slider
                                aria-label="Temperature"
                                defaultValue={0}
                                getAriaValueText={valuetext}
                                valueLabelDisplay="auto"
                                shiftStep={1}
                                step={1}
                                marks
                                min={0}
                                max={5}
                              />
                            </Typography>
                            <Typography
                              id="modal-modal-description"
                              sx={{ mt: 2 }}
                            >
                              <button
                                onClick={() =>
                                  demandeToMoveFunction(userDemande.id)
                                }
                                className="btn btn-success w-100 py-2"
                              >
                                ارسال
                              </button>
                            </Typography>
                          </Box>
                        </Modal>
                        {evaluationWorker === false &&
                        noAgreementWorker === false ? (
                          <button
                            onClick={() => noAgreement(userDemande.id)}
                            className="btn btn-danger  btn-customm col-6"
                          >
                            لم نتفاهم
                          </button>
                        ) : noAgreementWorker === true &&
                          evaluationWorker === false ? (
                          "لم نتفاهم"
                        ) : (
                          ""
                        )}
                        {evaluationWorker === true ||
                        noAgreementWorker === true ? (
                          <div
                            onClick={modifyEvaluationWorker}
                            style={{ cursor: "pointer" }}
                            className="col-2"
                          >
                            <FontAwesomeIcon icon={faEdit} />
                          </div>
                        ) : (
                          ""
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}

            {/* Pagination */}
            <div className="d-flex justify-content-between align-items-center mt-4">
              <div className="text-muted">1-3 من 10</div>
              <div className="d-flex gap-2">
                <button className="btn btn-outline-secondary btn-sm">
                  <ChevronRight size={18} />
                </button>
                <button className="btn btn-outline-secondary btn-sm">
                  <ChevronLeft size={18} />
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* <div className="d-flex flex-column gap-3">
        <div className="card">
          <div className="card-body ">
            <h5 class="card-title d-flex justify-content-start">
              طلبيات تم تقييمها:
            </h5>
            {userDemandesEvaluated.map((userDemande) => (
              <div className="d-flex flex-column gap-3">
                <div className="card">
                  <div className="card-body">
                    <div className="d-flex justify-content-between align-items-center">
                      <div className="d-flex align-items-center gap-3">
                        <div
                          style={{
                            width: "48px",
                            height: "48px",
                            position: "relative",
                          }}
                        >
                          <img
                            src={userDemande.review.avatar}
                            alt={userDemande.review.name}
                            width={48}
                            height={48}
                            className="rounded-circle"
                          />
                        </div>
                        <div>
                          <h6 className="mb-0">{userDemande.review.name}</h6>
                          <small className="text-muted">
                            {userDemande.review.profession}
                          </small>
                        </div>
                      </div>
                      <div className="d-flex gap-5">
                      {userDemande.agreement === false ? (
                          "لم نتفاهم"
                        ) : (
                          <div className="d-flex gap-1">
                          {[...Array(5)].map((_, index) => (
                            <Star
                              key={index}
                              size={20} // Augmenté de 20 à 40
                              className={`w-5 ${
                                userDemande.rating > index
                                  ? "text-warning"
                                  : "text-muted"
                              }`} // Augmenté w-5 à w-8 pour la cohérence
                              style={{
                                fill:
                                  userDemande.rating > index
                                    ? "#f39c12"
                                    : "none",
                                stroke:
                                  userDemande.rating > index ? "none" : "#ccc",
                                strokeWidth: 2,
                              }}
                            />
                          ))}
                        </div>
                        )}
                        

                        <div
                          onClick={() => modifyEvaluationWorker(userDemande.id)}
                          style={{ cursor: "pointer" }}
                        >
                          <FontAwesomeIcon icon={faEdit} />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}

            <div className="d-flex justify-content-between align-items-center mt-4">
              <div className="text-muted">1-3 من 10</div>
              <div className="d-flex gap-2">
                <button className="btn btn-outline-secondary btn-sm">
                  <ChevronRight size={18} />
                </button>
                <button className="btn btn-outline-secondary btn-sm">
                  <ChevronLeft size={18} />
                </button>
              </div>
            </div>
          </div>
        </div>
      </div> */}
    </div>
  );
}

// import "bootstrap/dist/css/bootstrap.min.css";
// import { ChevronLeft, ChevronRight, Star } from "lucide-react";
// import Slider from "@mui/material/Slider";
// import { Modal, Box, Typography } from "@mui/material";
// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faEdit } from "@fortawesome/free-solid-svg-icons";
// import { useState } from "react";
// import { useEffect } from "react";
// import "../styles/UserDemandeStyles.css";
// // import Image from 'next/image'
// const style = {
//   position: "absolute",
//   top: "50%",
//   left: "50%",
//   transform: "translate(-50%, -50%)",
//   width: 400,
//   bgcolor: "background.paper",
//   border: "2px solid #000",
//   boxShadow: 24,
//   p: 4,
// };
// export default function WorkerListing() {
//   const [rating, setRating] = useState(0);

//   // useEffect(() => {
//   //   console.log(rating);

//   // }, [rating]);

//   const valuetext = (value) => {
//     setRating(value);
//   };
//   // const [hover, setHover] = useState(0);
//   const [open, setOpen] = useState(false);
//   const handleOpen = () => setOpen(true);
//   const handleClose = () => setOpen(false);
//   const [evaluationWorker, setEvaluationWorker] = useState(false);
//   const [noAgreementWorker, setNoAgreementWorker] = useState(false);
//   const userDemande = require("../data/userDemandes.json");
//   const [userDemandesEvaluated, setUserDemandesEvaluated] = useState([]);
//   const [userDemandesNotEvaluated, setUserDemandesNotEvaluated] = useState([]);

//   // Initialisation initiale
//   useEffect(() => {
//     const evaluatedDemandes = userDemande.filter(
//       (demande) => demande.evaluation === true
//     );
//     const notEvaluatedDemandes = userDemande.filter(
//       (demande) => demande.evaluation === false
//     );

//     setUserDemandesEvaluated(evaluatedDemandes);
//     setUserDemandesNotEvaluated(notEvaluatedDemandes);
//   }, []);

//   const handleEvaluationWorker = () => {
//     handleOpen();
//     setEvaluationWorker(true);
//   };

//   const demandeToMoveFunction = (demandeId) => {

//     // Trouver la demande dans la liste non évaluée
//     const demandeToMove = userDemandesNotEvaluated.find(
//       (demande) => demande.id === demandeId
//     );
//     if (demandeToMove) {
//       //   // Modifier l'évaluation de la demande
//       const updatedDemande = { ...demandeToMove, evaluation: true, agreement : true , rating: rating};
//       //   // Retirer la demande de la liste non évaluée
//       setUserDemandesNotEvaluated((prev) =>
//         prev.filter((demande) => demande.id !== demandeId)
//       );
//       //   // Ajouter la demande à la liste évaluée
//       setUserDemandesEvaluated((prev) => [...prev, updatedDemande]);
//     }

//     handleClose();
//   };

//   const modifyEvaluationWorker = (demandeId) => {
//     // Trouver la demande dans la liste évaluée
//     const demandeToMove = userDemandesEvaluated.find(
//       (demande) => demande.id === demandeId
//     );

//     if (demandeToMove) {
//       // Modifier l'évaluation de la demande
//       const updatedDemande = { ...demandeToMove, evaluation: false };

//       // Retirer la demande de la liste évaluée
//       setUserDemandesEvaluated((prev) =>
//         prev.filter((demande) => demande.id !== demandeId)
//       );

//       // Ajouter la demande à la liste non évaluée
//       setUserDemandesNotEvaluated((prev) => [...prev, updatedDemande]);
//     }

//     setEvaluationWorker(false);
//     setNoAgreementWorker(false);
//   };

//   const noAgreement = (demandeId) => {
//     setEvaluationWorker(false);
//     setNoAgreementWorker(true);

//     // Trouver la demande dans la liste non évaluée
//     const demandeToMove = userDemandesNotEvaluated.find(
//       (demande) => demande.id === demandeId
//     );
//     if (demandeToMove) {
//       //   // Modifier l'évaluation de la demande
//       const updatedDemande = { ...demandeToMove, evaluation: true, agreement : false};
//       //   // Retirer la demande de la liste non évaluée
//       setUserDemandesNotEvaluated((prev) =>
//         prev.filter((demande) => demande.id !== demandeId)
//       );
//       //   // Ajouter la demande à la liste évaluée
//       setUserDemandesEvaluated((prev) => [...prev, updatedDemande]);
//     }

//     handleClose();
//   };

//   return (
//     <div className="container py-4 w-100" style={{ direction: "rtl" }}>
//       {/* Search Section */}
//       <div className="d-flex gap-2 mb-4 row">
//         <div className=" col-12 col-md-9">
//           <input
//             type="text"
//             className="form-control w-100"
//             placeholder="أدخل اسم العامل"
//           />
//         </div>

//         <div className="col-12 col-md-2">
//           <button className="btn btn-secondary px-4 w-100">بحث</button>
//         </div>
//       </div>

//       <div className="d-flex flex-column gap-3">
//         {/* First Worker */}
//         <div className="card">
//           <div className="card-body">
//             <h5 class="card-title d-flex justify-content-start">
//               طلبيات تنتضر تقييمك لها:{" "}
//             </h5>
//             {userDemandesNotEvaluated.map((userDemande) => (
//               <div className="d-flex flex-column gap-3">
//                 {/* First Worker */}
//                 <div className="card">
//                   <div className="card-body">
//                     <div className="d-flex justify-content-between align-items-center">
//                       <div className="d-flex align-items-center gap-3">
//                         <div
//                           style={{
//                             width: "48px",
//                             height: "48px",
//                             position: "relative",
//                           }}
//                         >
//                           <img
//                             src={userDemande.review.avatar}
//                             alt={userDemande.review.name}
//                             width={48}
//                             height={48}
//                             className="rounded-circle"
//                           />
//                         </div>
//                         <div>
//                           <h6 className="mb-0">{userDemande.review.name}</h6>
//                           <small className="text-muted">
//                             {userDemande.review.profession}
//                           </small>
//                         </div>
//                       </div>

//                       <div className="d-flex gap-3  row">
//                         {evaluationWorker === false &&
//                         noAgreementWorker === false ? (
//                           <button
//                             onClick={() => handleEvaluationWorker()}
//                             className="btn btn-success btn-customm col-6"
//                           >
//                             تم العمل
//                           </button>
//                         ) : evaluationWorker === false &&
//                           noAgreementWorker === true ? (
//                           ""
//                         ) : (
//                           <div className="d-flex gap-1 col-9">
//                             {[...Array(5)].map((_, index) => (
//                               <Star
//                                 key={index}
//                                 size={20} // Augmenté de 20 à 40
//                                 className={`w-5 ${
//                                   rating > index ? "text-warning" : "text-muted"
//                                 }`} // Augmenté w-5 à w-8 pour la cohérence
//                                 style={{
//                                   fill: rating > index ? "#f39c12" : "none",
//                                   stroke: rating > index ? "none" : "#ccc",
//                                   strokeWidth: 2,
//                                 }}
//                               />
//                             ))}
//                           </div>
//                         )}
//                         <Modal
//                           open={open}
//                           onClose={handleClose}
//                           aria-labelledby="modal-modal-title"
//                           aria-describedby="modal-modal-description"
//                         >
//                           <Box sx={style}>
//                             <Typography
//                               id="modal-modal-title"
//                               variant="h6"
//                               component="h2"
//                             >
//                               ما هو تقييمك لهدا العمل؟
//                             </Typography>
//                             <Typography
//                               id="modal-modal-description"
//                               sx={{ mt: 2 }}
//                             >
//                               <Slider
//                                 aria-label="Temperature"
//                                 defaultValue={0}
//                                 getAriaValueText={valuetext}
//                                 valueLabelDisplay="auto"
//                                 shiftStep={1}
//                                 step={1}
//                                 marks
//                                 min={0}
//                                 max={5}
//                               />
//                             </Typography>
//                             <Typography
//                               id="modal-modal-description"
//                               sx={{ mt: 2 }}
//                             >
//                               <button
//                                 onClick={() =>
//                                   demandeToMoveFunction(userDemande.id)
//                                 }
//                                 className="btn btn-success w-100 py-2"
//                               >
//                                 ارسال
//                               </button>
//                             </Typography>
//                           </Box>
//                         </Modal>
//                         {evaluationWorker === false &&
//                         noAgreementWorker === false ? (
//                           <button
//                             onClick={() => noAgreement(userDemande.id)}
//                             className="btn btn-danger  btn-customm col-6"
//                           >
//                             لم نتفاهم
//                           </button>
//                         ) : noAgreementWorker === true &&
//                           evaluationWorker === false ? (
//                           "لم نتفاهم"
//                         ) : (
//                           ""
//                         )}
//                         {evaluationWorker === true ||
//                         noAgreementWorker === true ? (
//                           <div
//                             onClick={modifyEvaluationWorker}
//                             style={{ cursor: "pointer" }}
//                             className="col-2"
//                           >
//                             <FontAwesomeIcon icon={faEdit} />
//                           </div>
//                         ) : (
//                           ""
//                         )}
//                       </div>
//                     </div>
//                   </div>
//                 </div>
//               </div>
//             ))}

//             {/* Pagination */}
//             <div className="d-flex justify-content-between align-items-center mt-4">
//               <div className="text-muted">1-3 من 10</div>
//               <div className="d-flex gap-2">
//                 <button className="btn btn-outline-secondary btn-sm">
//                   <ChevronRight size={18} />
//                 </button>
//                 <button className="btn btn-outline-secondary btn-sm">
//                   <ChevronLeft size={18} />
//                 </button>
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>

//       <div className="d-flex flex-column gap-3">
//         {/* First Worker */}
//         <div className="card">
//           <div className="card-body ">
//             <h5 class="card-title d-flex justify-content-start">
//               طلبيات تم تقييمها:
//             </h5>
//             {userDemandesEvaluated.map((userDemande) => (
//               <div className="d-flex flex-column gap-3">
//                 <div className="card">
//                   <div className="card-body">
//                     <div className="d-flex justify-content-between align-items-center">
//                       <div className="d-flex align-items-center gap-3">
//                         <div
//                           style={{
//                             width: "48px",
//                             height: "48px",
//                             position: "relative",
//                           }}
//                         >
//                           <img
//                             src={userDemande.review.avatar}
//                             alt={userDemande.review.name}
//                             width={48}
//                             height={48}
//                             className="rounded-circle"
//                           />
//                         </div>
//                         <div>
//                           <h6 className="mb-0">{userDemande.review.name}</h6>
//                           <small className="text-muted">
//                             {userDemande.review.profession}
//                           </small>
//                         </div>
//                       </div>
//                       <div className="d-flex gap-5">
//                       {userDemande.agreement === false ? (
//                           "لم نتفاهم"
//                         ) : (
//                           <div className="d-flex gap-1">
//                           {[...Array(5)].map((_, index) => (
//                             <Star
//                               key={index}
//                               size={20} // Augmenté de 20 à 40
//                               className={`w-5 ${
//                                 userDemande.rating > index
//                                   ? "text-warning"
//                                   : "text-muted"
//                               }`} // Augmenté w-5 à w-8 pour la cohérence
//                               style={{
//                                 fill:
//                                   userDemande.rating > index
//                                     ? "#f39c12"
//                                     : "none",
//                                 stroke:
//                                   userDemande.rating > index ? "none" : "#ccc",
//                                 strokeWidth: 2,
//                               }}
//                             />
//                           ))}
//                         </div>
//                         )}

//                         <div
//                           onClick={() => modifyEvaluationWorker(userDemande.id)}
//                           style={{ cursor: "pointer" }}
//                         >
//                           <FontAwesomeIcon icon={faEdit} />
//                         </div>
//                       </div>
//                     </div>
//                   </div>
//                 </div>
//               </div>
//             ))}

//             {/* Pagination */}
//             <div className="d-flex justify-content-between align-items-center mt-4">
//               <div className="text-muted">1-3 من 10</div>
//               <div className="d-flex gap-2">
//                 <button className="btn btn-outline-secondary btn-sm">
//                   <ChevronRight size={18} />
//                 </button>
//                 <button className="btn btn-outline-secondary btn-sm">
//                   <ChevronLeft size={18} />
//                 </button>
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// }
