import "bootstrap/dist/css/bootstrap.min.css";
import { Link } from "react-router-dom";
import { Phone, Star } from "lucide-react";
import { Modal, Box, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
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
export default function ContactCard() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const { id } = useParams();
  const [review, setReviews] = useState([]);
  // const [sousServices, setSousServices] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/servicePropose/" + id)
      .then((response) => {
        //setSousServices(response.data);
        setReviews(response.data.ouvrier);
      })
      .catch((error) => {
        console.error(
          "Erreur lors de la récupération de cette ouvrier :",
          error
        );
      });
  }, []);



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
              alt={review.name}
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
            {" "}
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
      <div className="card">
        <div className="card-body">
          {/* Phone Number Section */}
          <div className="d-flex justify-content-between align-items-center mb-3">
            <span className="fw-bold">الهاتف :</span>
            <div className="d-flex align-items-center gap-2">
              <Phone size={18} />
              <a
                href="tel:${review.phoneNumber}"
                className="text-decoration-none text-dark"
              >
                {review.numeroTelephone}
              </a>
            </div>
          </div>

          {/* Location Label */}
          <div className="mb-2">
            <span className="fw-bold">مكان العمل </span>
          </div>

          {/* Map Section */}
          <div className="mb-3">
            <div
              className="rounded overflow-hidden"
              style={{ height: "100px" }}
            >
              {/* <iframe
                src={review.localisation}
                width="100%"
                height="100%"
                style={{ border: 0 }}
                allowFullScreen
                loading="lazy"
                referrerPolicy="no-referrer-when-downgrade"
              /> */}
              <a
                href={review.localisation}
                target="_blank"
                rel="noopener noreferrer"
              >
                Voir sur Google Maps
              </a>
            </div>
          </div>

          <button onClick={handleOpen} className="btn btn-success w-100 py-2">
            {" "}
            أكمل معطيات الطلبية
          </button>
          <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
          >
            <Box sx={style}>
              <Typography
                className="position-absolute top-0 start-50"
                id="modal-modal-description"
                sx={{ mt: 2 }}
              >
                من فضلك هل اتصلت بالعميل ؟
              </Typography>
              <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                <Link
                  to={"/UserDemande"}
                  className="btn btn-success w-100 py-2"
                >
                  نعم
                </Link>
              </Typography>
            </Box>
          </Modal>
        </div>
      </div>
    </div>
  );
}
