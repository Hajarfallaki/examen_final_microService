import React from 'react';
import {
    AppBar, Toolbar, Typography, Drawer, List, ListItem,
    ListItemIcon, ListItemText, Box, Button
} from '@mui/material';
import { Link, useLocation } from 'react-router-dom';
import InventoryIcon from '@mui/icons-material/Inventory';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import BarChartIcon from '@mui/icons-material/BarChart';
import keycloak from '../../services/keycloak';

const drawerWidth = 240;

const AppLayout = ({ children }) => {
    const location = useLocation();

    const menuItems = [
        { text: 'Products', icon: <InventoryIcon />, path: '/products' },
        { text: 'Orders', icon: <ShoppingCartIcon />, path: '/orders' },
        { text: 'Analytics', icon: <BarChartIcon />, path: '/analytics' }
    ];

    return (
        <Box sx={{ display: 'flex' }}>
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
                        E-Commerce Management
                    </Typography>
                    <Typography variant="body2" sx={{ mr: 2 }}>
                        {keycloak.tokenParsed?.preferred_username}
                    </Typography>
                    <Button color="inherit" onClick={() => keycloak.logout()}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>

            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': { width: drawerWidth, boxSizing: 'border-box' }
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <List>
                        {menuItems.map((item) => (
                            <ListItem
                                button
                                key={item.text}
                                component={Link}
                                to={item.path}
                                selected={location.pathname === item.path}
                            >
                                <ListItemIcon>{item.icon}</ListItemIcon>
                                <ListItemText primary={item.text} />
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>

            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <Toolbar />
                {children}
            </Box>
        </Box>
    );
};

export default AppLayout;