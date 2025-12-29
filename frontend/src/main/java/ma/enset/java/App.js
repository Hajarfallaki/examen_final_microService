import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import keycloak from './services/keycloak';
import AppLayout from './components/Layout/AppLayout';
import ProductList from './components/Products/ProductList';
import OrderList from './components/Orders/OrderList';
import Analytics from './components/Analytics/Analytics';
import Login from './components/Auth/Login';

const theme = createTheme({
    palette: {
        mode: 'light',
        primary: { main: '#1976d2' },
        secondary: { main: '#dc004e' }
    }
});

function App() {
    const [authenticated, setAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        keycloak.init({ onLoad: 'check-sso' })
            .then((auth) => {
                setAuthenticated(auth);
                setLoading(false);

                if (auth) {
                    setInterval(() => {
                        keycloak.updateToken(70).catch(() => {
                            keycloak.logout();
                        });
                    }, 60000);
                }
            })
            .catch(() => {
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Router>
                {authenticated ? (
                    <AppLayout>
                        <Routes>
                            <Route path="/products" element={<ProductList />} />
                            <Route path="/orders" element={<OrderList />} />
                            <Route path="/analytics" element={<Analytics />} />
                            <Route path="/" element={<Navigate to="/products" />} />
                        </Routes>
                    </AppLayout>
                ) : (
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route path="*" element={<Navigate to="/login" />} />
                    </Routes>
                )}
            </Router>
        </ThemeProvider>
    );
}
