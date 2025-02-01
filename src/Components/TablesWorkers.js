import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import { Star, ChevronLeft, ChevronRight } from "lucide-react";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js"; // Required for dropdowns to function

export default function TablesWorkers() {
  const reviewsList = require("../data/review.json");

  const [reviews, setReviews] = useState(reviewsList);
  const [page, setPage] = useState(1);

  useEffect(() => {
    const filteredReviews = reviewsList.filter(
      (review) => review.pagination.currentPage === page
    );
    setReviews(filteredReviews);
  }, [page]);

  const pageSuivante = () => {
    if (page < 4) {
      // Selon vos données, vous avez 4 pages
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
        <div className="dropdown flex-grow-1 col-12 col-md-4">
          <button
            className="btn btn-light w-100 text-start d-flex justify-content-between align-items-center"
            type="button"
            data-bs-toggle="dropdown"
          >
            اختر المهنة
            <ChevronLeft className="ms-2" size={20} />
          </button>
          <ul className="dropdown-menu w-100">
            <li>
              <button className="dropdown-item">نجار</button>
            </li>
            <li>
              <button className="dropdown-item">محامي</button>
            </li>
            <li>
              <button className="dropdown-item">Triporteur</button>
            </li>
          </ul>
        </div>

        <div className="dropdown flex-grow-1 col-12 col-md-4">
          <button
            className="btn btn-light w-100 text-start d-flex justify-content-between align-items-center"
            type="button"
            data-bs-toggle="dropdown"
          >
            اختر المدينة
            <ChevronLeft className="ms-2" size={20} />
          </button>
          <ul className="dropdown-menu w-100">
            <li>
              <button className="dropdown-item">الرباط</button>
            </li>
            <li>
              <button className="dropdown-item">الدار البيضاء</button>
            </li>
            <li>
              <button className="dropdown-item">فاس</button>
            </li>
          </ul>
        </div>
        <div className="col-12 col-md-2">
          <button className="btn btn-secondary px-4  w-100">بحث</button>
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
                    alt={review.name}
                    width={50}
                    height={50}
                    className="rounded-circle"
                  />

                  <div>
                    <h6 className="mb-0">{review.name}</h6>
                    <small className="text-muted">{review.profession}</small>
                  </div>
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
        <span>{page}</span>
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
            disabled={page >= 4}
          >
            <ChevronLeft size={20} />
          </button>
        </div>
      </div>
    </div>
  );
}
