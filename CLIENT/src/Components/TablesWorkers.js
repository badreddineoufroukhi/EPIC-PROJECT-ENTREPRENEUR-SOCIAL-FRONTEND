import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import { Star, ChevronLeft, ChevronRight } from "lucide-react";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js"; // Required for dropdowns to function
import axios from "axios";

export default function TablesWorkers() {
  const reviewsList = [];
  const [selectedProfessionToSearch, setSelectedProfessionToSearch] = useState("");
  const [selectedCityToSearch, setSelectedCityToSearch] = useState("");
  const [selectedProfessionToShow, setSelectedProfessionToShow] = useState("");
  const [selectedCityToShow, setSelectedCityToShow] = useState("");

  const [reviews, setReviews] = useState(reviewsList);
  const [page, setPage] = useState(1);
  const [dataSize, setDataSize] = useState(0);

  const handleProfessionChange = (e) => {
    setPage(1)
    const value = e.target.value;
    setSelectedProfessionToShow(value)
    if (value.length >= 2) {
      setSelectedProfessionToSearch(value);
    }
    else {
      setSelectedProfessionToSearch("");
    }
  };

  const handleCityChange = (e) => {
    setPage(1)
    const value = e.target.value;
    setSelectedCityToShow(value)
    if (value.length >= 2) {
      setSelectedCityToSearch(value);
    }
    else {
      setSelectedCityToSearch("");
    }
  };

  useEffect(() => {
    axios
      .post("http://localhost:8080/ouvrier/paginatedListByCriteria", {
        page: page - 1,
        maxResults: 3,
        sortOrder: "desc",
        sortField: "rating",
        disponible: true,
        villeLike: selectedCityToSearch,
        professionLike: selectedProfessionToSearch
      })
      .then((response) => {
        setReviews(response.data.list || []);
        setDataSize(response.data.dataSize || 0);
      })
      .catch((error) => {
        console.error("Erreur lors de la récupération des ouvriers :", error);
      });
  }, [page, selectedCityToSearch, selectedProfessionToSearch]);


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
      {/* Search Filters */}
      <div className="d-flex gap-3 mb-4 row">
      <div className="flex-grow-1 col-12 col-md-4">
        <input
          type="text"
          className="form-control"
          placeholder="اختر المهنة (أدخل 2 حروف)"
          value={selectedProfessionToShow}
          onChange={handleProfessionChange}
        />
      </div>

      <div className="flex-grow-1 col-12 col-md-4">
        <input
          type="text"
          className="form-control"
          placeholder="اختر المدينة (أدخل 2 حروف)"
          value={selectedCityToShow}
          onChange={handleCityChange}
        />
      </div>
    </div>

      {/* Reviews List */}
      <div className="reviews-list">
        {reviews.map((review) => (
          <Link to={"/ListeSousServices/" + review.id}>
            <div key={review.id} className="card mb-3 p-3">
              <div className="d-flex justify-content-between align-items-center">
                <div className="d-flex align-items-center gap-3">
                  <img
                    src={review.avatar}
                    alt={review.nom}
                    width={50}
                    height={50}
                    className="rounded-circle"
                  />

                  <div>
                    <h6 className="mb-0">{review.nom} {review.prenom}</h6>
                    <small className="text-muted">{review.profession} من {review.ville}</small>
                  </div>
                </div>
                  <div>
                  <small className="text-muted">"{review.description}"</small>
                  </div>

                <div className="d-flex gap-1">
                  {[...Array(5)].map((_, index) => (
                    <Star
                      key={index}
                      size={20}
                      className={
                        index < review.rating ? "text-warning" : "text-muted"
                      } // Color the filled star with 'text-warning'
                      style={{
                        fill: index < review.rating ? "#f39c12" : "none", // Fill the star with color
                        stroke: index < review.rating ? "none" : "#ccc", // Optional: Make sure the empty stars have a border color
                        strokeWidth: "2",
                      }}
                    />
                  ))}
                </div>
              </div>
            </div>
          </Link>
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
