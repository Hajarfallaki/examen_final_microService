import React, { useEffect, useState } from 'react';
import {
    Card, CardContent, Typography, Grid, Button, Dialog,
    DialogTitle, DialogContent, TextField, DialogActions
} from '@mui/material';
import { inventoryApi } from '../../services/api';

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [open, setOpen] = useState(false);
    const [newProduct, setNewProduct] = useState({
        name: '', price: 0, quantity: 0, categoryId: ''
    });

    useEffect(() => {
        loadProducts();
    }, []);

    const loadProducts = async () => {
        try {
            const response = await inventoryApi.getAllProducts();
            setProducts(response.data);
        } catch (error) {
            console.error('Error loading products:', error);
        }
    };

    const handleCreateProduct = async () => {
        try {
            await inventoryApi.createProduct(newProduct);
            setOpen(false);
            loadProducts();
            setNewProduct({ name: '', price: 0, quantity: 0, categoryId: '' });
        } catch (error) {
            console.error('Error creating product:', error);
        }
    };

    return (
        <>
            <Typography variant="h4" gutterBottom>Products</Typography>
            <Button variant="contained" onClick={() => setOpen(true)} sx={{ mb: 2 }}>
                Add Product
            </Button>

            <Grid container spacing={3}>
                {products.map((product) => (
                    <Grid item xs={12} sm={6} md={4} key={product.productId}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6">{product.name}</Typography>
                                <Typography color="textSecondary">Price: ${product.price}</Typography>
                                <Typography color="textSecondary">Stock: {product.quantity}</Typography>
                                <Typography color="textSecondary">Status: {product.status}</Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Dialog open={open} onClose={() => setOpen(false)}>
                <DialogTitle>Add New Product</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Product Name"
                        fullWidth
                        value={newProduct.name}
                        onChange={(e) => setNewProduct({...newProduct, name: e.target.value})}
                    />
                    <TextField
                        margin="dense"
                        label="Price"
                        type="number"
                        fullWidth
                        value={newProduct.price}
                        onChange={(e) => setNewProduct({...newProduct, price: parseFloat(e.target.value)})}
                    />
                    <TextField
                        margin="dense"
                        label="Quantity"
                        type="number"
                        fullWidth
                        value={newProduct.quantity}
                        onChange={(e) => setNewProduct({...newProduct, quantity: parseInt(e.target.value)})}
                    />
                    <TextField
                        margin="dense"
                        label="Category ID"
                        fullWidth
                        value={newProduct.categoryId}
                        onChange={(e) => setNewProduct({...newProduct, categoryId: e.target.value})}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpen(false)}>Cancel</Button>
                    <Button onClick={handleCreateProduct} variant="contained">Create</Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default ProductList;