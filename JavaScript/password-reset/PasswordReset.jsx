import React, { useState } from 'react';
import {useNavigate} from 'react-router-dom';
import Header from '../../components/header/Header';
import './PasswordReset.css';
import { generateRandomCode } from '../../utils/functions/generateRandomCode';
import { isValidEmail } from '../../utils/functions/generateRandomCode';
import { Button, Box, Typography } from '@mui/material'; 


const PasswordReset = () => {

  const [email, setEmail] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const code = generateRandomCode(); 
    console.log(`Un cod de verificare a fost trimis la adresa: ${email}`);
    console.log(`Cod de verificare ngenerat: ${code}`);
    
  };

  return (
    <Box>
         <Header text="Forgot your password?" />

       <Typography sx={{ fontSize: '15px', marginTop: '10px', marginBottom: '2px' }} variant="h6" className="password-heading">
       Please enter your email
      </Typography>


        <form onSubmit={handleSubmit} className="password-form">
          <label className = "form-label">

          <label  hidden for="email-input">Email address</label>
            <input 
             name = "email-input"
              placeholder = 'Your email address'
              type = "email"
              value = {email}
              onChange = {(e) => setEmail(e.target.value)}
              required
              className = "form-input form-label"
            />
          </label>

            <button 
            onClick={() => navigate("/verify-code")}
            type = "submit" 
            className = "form-button" 
            disabled = {!isValidEmail(email) || email.length < 10} >
              Reset password
            </button>

        </form>
    </Box>
  );
};

export default PasswordReset;