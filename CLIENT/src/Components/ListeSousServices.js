import "bootstrap/dist/css/bootstrap.min.css";
import { ChevronLeft, ChevronRight, Star } from "lucide-react";
import { Link, useParams } from "react-router-dom";
import { Modal, Box, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import axios from "axios";
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
export default function ListeSousServices() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [review, setReviews] = useState([]);
  const { id } = useParams();
  const [sousServices, setSousServices] = useState([]);
  const [page, setPage] = useState(1);
  const [dataSize, setDataSize] = useState(0);
  const [selectedServiceToSearch, setSelectedServiceToSearch] = useState("");
  const [selectedServiceToShow, setSelectedServiceToShow] = useState("");

  const handleServiceChange = (e) => {
    setPage(1);
    const value = e.target.value;
    setSelectedServiceToShow(value);
    if (value.length >= 2) {
      setSelectedServiceToSearch(value);
    } else {
      setSelectedServiceToSearch("");
    }
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/ouvrier/" + id)
      .then((response) => {
        setReviews(response.data);
      })
      .catch((error) => {
        console.error(
          "Erreur lors de la récupération de cette ouvrier :",
          error
        );
      });
  }, []);

  useEffect(() => {
    axios
      .post("http://localhost:8080/servicePropose/paginatedListByCriteria", {
        page: page - 1,
        maxResults: 1,
        sortOrder: "desc",
        sortField: "rating",
        ouvrierId: parseInt(id),
        nameLike: selectedServiceToSearch,
      })
      .then((response) => {
        setSousServices(response.data.list || []);
        setDataSize(response.data.dataSize || 0);
      })
      .catch((error) => {
        console.error("Erreur lors de la récupération des ouvriers :", error);
      });
  }, [page, selectedServiceToSearch]);

  const AjouterDemande = (sousService_Id) => {
    axios
      .post("http://localhost:8080/demande", {
        client: {
          id: 6,
        },
        servicePropose: {
          id: parseInt(sousService_Id),
        },
        agreement: "NOTEVALYATED",
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(
          "Erreur lors de la récupération de cette ouvrier :",
          error
        );
      });
  };

  const pageSuivante = () => {
    if (page < dataSize) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  const pagePrecedante = () => {
    if (page > 1) {
      setPage((prevPage) => prevPage - 1);
    }
  };

  return (
    <div className="container py-4 W-100" style={{ direction: "rtl" }}>
      {/* Profile Card */}
      <div className="card mb-4 text-center">
        <div className="card-body">
          <div
            className="mx-auto mb-2"
            style={{ width: "150px", height: "150px", position: "relative" }}
          >
            <img
              src={review.avatar}
              alt={review.nom}
              width={150}
              height={150}
              className="rounded-circle"
              style={{ objectFit: "cover" }}
            />
          </div>
          <h5 className="card-title mb-1">
            {review.nom} {review.prenom}
          </h5>
          <p className="text-muted small mb-2">
            {review.profession} من {review.ville}
          </p>
          <div className="d-flex justify-content-center gap-1">
            {[...Array(5)].map((_, i) => (
              <Star
                key={i}
                size={20}
                className={i < review.rating ? "text-warning" : "text-muted"} // Color the filled star with 'text-warning'
                style={{
                  fill: i < review.rating ? "#f39c12" : "none", // Fill the star with color
                  stroke: i < review.rating ? "none" : "#ccc", // Optional: Make sure the empty stars have a border color
                  strokeWidth: "2",
                }}
              />
            ))}
          </div>
        </div>
      </div>

      {/* Work Type Selector */}
      <div className="card mb-4">
        <div className="card-body">
          <input
            type="text"
            className="form-control"
            placeholder="اختر العمل الذي تريد (أدخل 2 حروف)"
            value={selectedServiceToShow}
            onChange={handleServiceChange}
          />
        </div>
      </div>

      {/* Desk Section */}
      {sousServices.map((sousService) => (
        <div key={sousService.id} className="card mb-4">
          <div className="card-body p-2">
            <div className="position-relative mb-3">
              <img
                src={sousService.avatar}
                alt={sousService.name}
                width={500}
                height={250}
                className="w-100 rounded"
                style={{ objectFit: "cover" }}
              />

              <button
                className="btn btn-light position-absolute"
                style={{
                  left: "10px",
                  top: "50%",
                  transform: "translateY(-50%)",
                }}
              >
                <ChevronLeft size={20} />
              </button>
              <button
                className="btn btn-light position-absolute"
                style={{
                  right: "10px",
                  top: "50%",
                  transform: "translateY(-50%)",
                }}
              >
                <ChevronRight size={20} />
              </button>
            </div>
            <div className="d-flex justify-content-between align-items-center px-2">
              <h5 className="mb-0">{sousService.name}</h5>
              <div className="d-flex justify-content-center gap-1">
                {[...Array(5)].map((_, i) => (
                  <Star
                    key={i}
                    size={20}
                    className={
                      i < sousService.rating ? "text-warning" : "text-muted"
                    } // Color the filled star with 'text-warning'
                    style={{
                      fill: i < sousService.rating ? "#f39c12" : "none", // Fill the star with color
                      stroke: i < sousService.rating ? "none" : "#ccc", // Optional: Make sure the empty stars have a border color
                      strokeWidth: "2",
                    }}
                  />
                ))}
              </div>
              <span className="text-muted small">
                ابتداءا من {sousService.prix}
              </span>
            </div>
            <div className="mt-2">
              <div className="card-body d-flex gap-2 p-2">
                <button
                  onClick={handleOpen}
                  className="btn btn-success flex-fill"
                >
                  أريد هدا العمل
                </button>
                <Modal
                  open={open}
                  onClose={handleClose}
                  aria-labelledby="modal-modal-title"
                  aria-describedby="modal-modal-description"
                >
                  <Box sx={style}>
                    <Typography
                      id="modal-modal-description"
                      style={{
                        width: "100%",
                        padding: "8px",
                        borderRadius: "4px",
                        border: "1px solid #ccc",
                        textAlign: "right",
                        direction: "rtl", 
                      }}
                      sx={{ mt: 2 }}
                    >
                      {sousService.description}
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                      <Link
                        to={"/InfoSensibleWorker/" + sousService.id}
                        className="btn btn-success w-100 py-2"
                        onClick={() => AjouterDemande(sousService.id)}
                      >
                        نعم
                      </Link>
                    </Typography>
                  </Box>
                </Modal>
              </div>
            </div>
          </div>
        </div>
      ))}

      <div className="d-flex justify-content-between align-items-center mt-4">
        <span>
          {page} / {dataSize}
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
            disabled={page >= dataSize}
          >
            <ChevronLeft size={20} />
          </button>
        </div>
      </div>

      {/* Action Buttons */}
      {/* <div className="card position-sticky bottom-0">
        <div className="card-body d-flex gap-2 p-2">
          <button onClick={handleOpen} className="btn btn-success flex-fill">
            أريد الاتصال بالعميل
          </button>
          <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
          >
            <Box sx={style}>
              <Typography
                id="modal-modal-description"
                className="position-absolute top-0 start-50"
                sx={{ mt: 2 }}
              >
                {sousService.description}
              </Typography>
              <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                <Link
                  to={"/InfoSensibleWorker/" + review.id}
                  className="btn btn-success w-100 py-2"
                >
                  نعم
                </Link>
              </Typography>
            </Box>
          </Modal>

          <Link
            to={"/TablesWorkers"}
            className="btn btn-outline-secondary flex-fill"
          >
            رجوع
          </Link>
        </div>
      </div> */}
    </div>
  );
}
