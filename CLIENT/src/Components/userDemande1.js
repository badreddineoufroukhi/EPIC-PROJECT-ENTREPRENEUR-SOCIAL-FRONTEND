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
import TextareaAutosize from "@mui/material/TextareaAutosize";

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

export default function UserDemande1() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState("");
  const [demande, setDemandes] = useState([]);
  const [page, setPage] = useState(1);
  const [dataSize, setDataSize] = useState(0);
  const [evaluation, setEvaluation] = useState();
  const [listeApreUpdate, setListeApreUpdate] = useState(false);

  const valuetext = (value) => {
    setRating(value);
  };


  const handleEvaluationWorker = (demande_description) => {
    setComment(demande_description);
    handleOpen();
  };
  const handleUpdateDemande = (
    demande_id,
    client_id,
    sousService_Id,
    rating_updated,
    agreement_updated,
    description_updated
  ) => {
    axios
      .put("http://localhost:8080/demande/" + demande_id, {
        rating: rating_updated,
        agreement: agreement_updated,
        description: description_updated,
        client: {
          id: parseInt(client_id),
        },
        servicePropose: {
          id: parseInt(sousService_Id),
        },
      })
      .then((response) => {
        setListeApreUpdate(true);
        if(agreement_updated === "EVALUATED")  handleClose();
      })
      .catch((error) => {
        console.error("Erreur lors de la récupération des ouvriers :", error);
      });
  };

  const handleChange = (event) => {
    setEvaluation(event.target.value);
  };

  useEffect(() => {
    axios
      .post("http://localhost:8080/demande/paginatedListByCriteria", {
        page: page - 1,
        maxResults: 3,
        sortOrder: "",
        sortField: "",
        agreement: evaluation,
      })
      .then((response) => {
        setDemandes(response.data.list || []);
        setDataSize(response.data.dataSize || 0);
        setListeApreUpdate(false);
      })
      .catch((error) => {
        console.error("Erreur lors de la récupération des ouvriers :", error);
      });
  }, [page, evaluation, listeApreUpdate]);

  const pageSuivante = () => {
    if (page < (dataSize / 3 + 1).toFixed(0)) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  const pagePrecedante = () => {
    if (page > 1) {
      setPage((prevPage) => prevPage - 1);
    }
  };

  return (
    <div className="container-fluid p-2 W-100" dir="rtl">
      {/* Search Section */}
      <div className="d-flex gap-2 mb-4 row">
        <div className="col-12">
          <select
            class="form-select"
            aria-label="Sélectionnez la situation de la commande"
            value={evaluation}
            onChange={handleChange}
          >
            <option selected value="">
              اختر حالة الطلبية
            </option>
            <option value="EVALUATED">طلبيات تم تقييمها</option>
            <option value="NOTEVALYATED">طلبيات تنتضر تقييمك لها</option>
            <option value="NOAGREEMENT">لم نتفاهم</option>
          </select>
        </div>
      </div>

      {/* Reviews List */}
      <div className="reviews-list">
        {demande.map((demande) => (
          <div key={demande.id} className="card mb-3 p-3">
            <div className="d-flex justify-content-between align-items-center">
              <div className="d-flex align-items-center gap-3">
                <img
                  src={demande.servicePropose.ouvrier.avatar}
                  alt={demande.servicePropose.ouvrier.name}
                  width={50}
                  height={50}
                  className="rounded-circle"
                />

                <div>
                  <h6 className="mb-0">
                    {demande.servicePropose.ouvrier.nom}{" "}
                    {demande.servicePropose.ouvrier.prenom}
                  </h6>
                  <small className="text-muted">
                    {demande.servicePropose.name}
                  </small>
                </div>
              </div>
              
              <div className="d-flex gap-3  row">
                {demande.agreement === "NOTEVALYATED" ? (
                  <button
                    onClick={() => handleEvaluationWorker(demande.description)}
                    className="btn btn-success btn-customm col-6"
                  >
                    تم العمل
                  </button>
                ) : demande.agreement === "NOAGREEMENT" ? (
                  ""
                ) : (
                  <div className="d-flex gap-1 col-9">
                    {[...Array(5)].map((_, index) => (
                      <Star
                        key={index}
                        size={20} // Augmenté de 20 à 40
                        className={`w-5 ${
                          demande.rating > index ? "text-warning" : "text-muted"
                        }`} // Augmenté w-5 à w-8 pour la cohérence
                        style={{
                          fill: demande.rating > index ? "#f39c12" : "none",
                          stroke: demande.rating > index ? "none" : "#ccc",
                          strokeWidth: 2,
                        }}
                      />
                    ))}
                  </div>
                )}
                {/* <Modal
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
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
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
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                      <button
                        onClick={() => handleUpdateDemande(
                          demande.id,
                          demande.client.id,
                          demande.servicePropose.id,
                          rating,
                          "EVALUATED",
                          ""
                        )}
                        className="btn btn-success w-100 py-2"
                      >
                        ارسال
                      </button>
                    </Typography>
                  </Box>
                </Modal> */}
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
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                      <TextareaAutosize
                        minRows={3}
                        placeholder="اكتب ملاحظاتك هنا..."
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        style={{
                          width: "100%",
                          padding: "8px",
                          borderRadius: "4px",
                          border: "1px solid #ccc",
                        }}
                      />
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
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
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                      <button
                        onClick={() =>
                          handleUpdateDemande(
                            demande.id,
                            demande.client.id,
                            demande.servicePropose.id,
                            rating,
                            "EVALUATED",
                            comment
                          )
                        }
                        className="btn btn-success w-100 py-2"
                      >
                        ارسال
                      </button>
                    </Typography>
                  </Box>
                </Modal>
                {demande.agreement === "NOTEVALYATED" ? (
                  <button
                    onClick={() =>
                      handleUpdateDemande(
                        demande.id,
                        demande.client.id,
                        demande.servicePropose.id,
                        0,
                        "NOAGREEMENT",
                        ""
                      )
                    }
                    className="btn btn-danger  btn-customm col-6"
                  >
                    لم نتفاهم
                  </button>
                ) : demande.agreement === "NOAGREEMENT" ? (
                  "لم نتفاهم"
                ) : (
                  ""
                )}
                {demande.agreement === "EVALUATED" ? (
                  <div
                    onClick={() =>
                      handleUpdateDemande(
                        demande.id,
                        demande.client.id,
                        demande.servicePropose.id,
                        demande.rating,
                        "NOTEVALYATED",
                        demande.description
                      )
                    }
                    style={{ cursor: "pointer" }}
                    className="col-2"
                  >
                    <FontAwesomeIcon icon={faEdit} />
                  </div>
                ) : demande.agreement === "NOAGREEMENT" ? (
                  <div
                    onClick={() =>
                      handleUpdateDemande(
                        demande.id,
                        demande.client.id,
                        demande.servicePropose.id,
                        demande.rating,
                        "NOTEVALYATED",
                        demande.description
                      )
                    }
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
        ))}
      </div>

      <div className="d-flex justify-content-between align-items-center mt-4">
        <span>
          {page} / {(dataSize / 3 + 1).toFixed(0)}
        </span>
        <div className="d-flex gap-2">
          <button
            onClick={pagePrecedante}
            className="btn btn-light"
            disabled={page <= 1}
          >
            <ChevronRight size={20} />
          </button>
          <button
            onClick={pageSuivante}
            className="btn btn-light"
            disabled={page >= (dataSize / 3 + 1).toFixed(0)}
          >
            <ChevronLeft size={20} />
          </button>
        </div>
      </div>
    </div>
  );
}
