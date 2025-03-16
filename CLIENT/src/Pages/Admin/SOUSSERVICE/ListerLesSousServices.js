"use client";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
// import { Chip } from "@mui/material";

import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  TextField,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Typography,
  Alert,
  CircularProgress,
  Grid,
} from "@mui/material";
import {
  Search,
  Refresh,
  Add,
  Visibility,
  Edit,
  Delete,
  KeyboardArrowUp,
  KeyboardArrowDown,
} from "@mui/icons-material";

export default function ServiceProposeList() {
  const [serviceProposes, setServiceProposes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [selectedServiceProposes, setSelectedServiceProposes] = useState([]);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [sortField, setSortField] = useState("");
  const [sortOrder, setSortOrder] = useState("desc");
  const [filters, setFilters] = useState({});
  const [error, setError] = useState("");

  const fetchServiceProposes = async () => {
    setLoading(true);
    try {
      const payload = {
        page,
        maxResults: rowsPerPage,
        sortField,
        sortOrder,
        ...filters,
      };

      const response = await axios.post(
        "http://localhost:8080/servicePropose/paginatedListByCriteria",
        payload,
        {
          headers: { "Content-Type": "application/json" },
        }
      );

      const data = response.data;
      setServiceProposes(data.list || []);
      setTotal(data.dataSize || 0);
      setError("");
    } catch (error) {
      console.error("Error fetching serviceProposes:", error);
      setError("Erreur lors du chargement des serviceProposes");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchServiceProposes();
  }, [page, rowsPerPage, sortField, sortOrder]);

  const handleSort = (column) => {
    const isAsc = sortField === column && sortOrder === "asc";
    setSortOrder(isAsc ? "desc" : "asc");
    setSortField(column);
  };

  const handleSelectAll = (event) => {
    setSelectedServiceProposes(event.target.checked ? serviceProposes : []);
  };

  const handleSelectServicePropose = (servicePropose) => {
    setSelectedServiceProposes((prevSelected) => {
      const isSelected = prevSelected.find((h) => h.id === servicePropose.id);
      return isSelected
        ? prevSelected.filter((h) => h.id !== servicePropose.id)
        : [...prevSelected, servicePropose];
    });
  };

  const handleDelete = async () => {
    const selectedServiceProposesToSend = selectedServiceProposes.map(
      (selectedServicePropose) => selectedServicePropose.id
    );
    try {
      await fetch("http://localhost:8080/servicePropose/delete", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selectedServiceProposesToSend),
      });
      setDeleteDialogOpen(false);
      setSelectedServiceProposes([]);
      fetchServiceProposes();
      setError("");
    } catch (error) {
      setError("Erreur lors de la suppression");
    }
  };

  const SortIcon = ({ field }) => {
    if (sortField !== field) return null;
    return sortOrder === "asc" ? (
      <KeyboardArrowUp fontSize="small" />
    ) : (
      <KeyboardArrowDown fontSize="small" />
    );
  };

  return (
    <div className="container-fluid mt-4">
      {/* Search Card */}
      <Paper className="p-4 mb-4">
        <Typography variant="h6" className="mb-3">
          Recherche des services Proposes
        </Typography>

        <Grid container spacing={2} sx={{ mb: 2 }}>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Name"
              variant="outlined"
              value={filters.nameLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, nameLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Rating"
              type="number"
              variant="outlined"
              value={filters.rating || ""}
              onChange={(e) =>
                setFilters({ ...filters, rating: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Prix"
              type="number"
              variant="outlined"
              value={filters.prix || ""}
              onChange={(e) =>
                setFilters({ ...filters, prix: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Description"
              variant="outlined"
              value={filters.descriptionLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, descriptionLike: e.target.value })
              }
            />
          </Grid>
        </Grid>

        <div className="flex justify-end">
          <Button
            variant="outlined"
            startIcon={<Refresh />}
            onClick={() => {
              setFilters({ nameLike: "", rating: 0, prix: 0.00, descriptionLike: "" });
              fetchServiceProposes();
            }}
            className="me-2"
          >
            Réinitialiser
          </Button>
          <Button
            variant="contained"
            startIcon={<Search />}
            onClick={fetchServiceProposes}
          >
            Rechercher
          </Button>
        </div>
      </Paper>

      {/* Results Card */}
      <Paper className="p-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <Typography variant="h6">
            Liste des services Proposes ({total})
          </Typography>
          <div className="flex justify-end">
            {selectedServiceProposes.length > 0 && (
              <IconButton
                color="error"
                onClick={() => setDeleteDialogOpen(true)}
                className="me-2"
              >
                <Delete />
              </IconButton>
            )}
            <Button
              variant="contained"
              startIcon={<Add />}
              component={Link}
              href="/crud_ServicePropose/Add_Edit_ServicePropose"
            >
              Ajouter
            </Button>
          </div>
        </div>

        {error && (
          <Alert severity="error" className="mb-3">
            {error}
          </Alert>
        )}

        {loading ? (
          <div className="text-center">
            <CircularProgress />
          </div>
        ) : (
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox">
                    <Checkbox
                      checked={
                        serviceProposes.length > 0 &&
                        selectedServiceProposes.length ===
                          serviceProposes.length
                      }
                      onChange={handleSelectAll}
                    />
                  </TableCell>
                  <TableCell onClick={() => handleSort("name")}>
                    Name <SortIcon field="name" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("rating")}>
                    Rating <SortIcon field="rating" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("prix")}>
                    Prix <SortIcon field="prix" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("description")}>
                    Description <SortIcon field="description" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("ouvrier.nom")}>
                    Nom d'ouvrier <SortIcon field="ouvrier.nom" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("ouvrier.prenom")}>
                    Prenom d'ouvrier <SortIcon field="ouvrier.prenom" />
                  </TableCell>
                  <TableCell align="center">Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {serviceProposes.map((servicePropose) => (
                  <TableRow key={servicePropose.id} hover>
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={selectedServiceProposes.some(
                          (h) => h.id === servicePropose.id
                        )}
                        onChange={() =>
                          handleSelectServicePropose(servicePropose)
                        }
                      />
                    </TableCell>
                    <TableCell>{servicePropose.name}</TableCell>
                    <TableCell>{servicePropose.rating}</TableCell>
                    <TableCell>{servicePropose.prix}</TableCell>
                    <TableCell>{servicePropose.description}</TableCell>
                    <TableCell>{servicePropose.ouvrier.nom}</TableCell>
                    <TableCell>{servicePropose.ouvrier.prenom}</TableCell>
                    <TableCell align="center">
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_ServicePropose/View_ServicePropose",
                          query: { id: servicePropose.id },
                        }}
                        color="primary"
                      >
                        <Visibility />
                      </IconButton>
                      <IconButton
                        component={Link}
                        href={{
                          pathname:
                            "/crud_ServicePropose/Add_Edit_ServicePropose",
                          query: { id: servicePropose.id },
                        }}
                        color="secondary"
                      >
                        <Edit />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}

        <div className="d-flex justify-content-between align-items-center mt-3">
          <div>
            <select
              className="form-select"
              value={rowsPerPage}
              onChange={(e) => setRowsPerPage(Number(e.target.value))}
            >
              {[5, 10, 50].map((size) => (
                <option key={size} value={size}>
                  {size} par page
                </option>
              ))}
            </select>
          </div>
          <div className="flex justify-end">
            <Button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
              Précédent
            </Button>
            <span className="mx-2">
              Page {page + 1} sur {Math.ceil(total / rowsPerPage)}
            </span>
            <Button
              disabled={page >= Math.ceil(total / rowsPerPage) - 1}
              onClick={() => setPage((p) => p + 1)}
            >
              Suivant
            </Button>
          </div>
        </div>
      </Paper>

      {/* Delete Dialog */}
      <Dialog
        open={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
      >
        <DialogTitle>Voulez-vous supprimer ces éléments ?</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {selectedServiceProposes.length === 1
              ? "Un ServicePropose va être supprimé"
              : `${selectedServiceProposes.length} serviceProposes vont être supprimés`}
          </DialogContentText>
          <DialogContentText>
            <b>NB :</b> Tous les demandes de cette servicePropose vont étre
            supprimer
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>Annuler</Button>
          <Button onClick={handleDelete} color="error">
            Supprimer
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
