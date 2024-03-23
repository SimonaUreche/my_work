import React, { useState } from 'react';
import Header from '../../components/header/Header';
import './VerifyCode.css'; 
import {useNavigate} from 'react-router-dom';
import {Box, Typography } from '@mui/material'; 
import { generateRandomCode } from '../../utils/functions/generateRandomCode';


const VerifyCode = () => {

  const [verificationCodes, setVerificationCodes] = useState(['', '', '', '', '', '']);
  const navigate = useNavigate();

  const handleCodeChange = (index, value) => {
    const updatedCodes = [...verificationCodes];
    updatedCodes[index] = value;
    setVerificationCodes(updatedCodes);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const code = verificationCodes.join('');
    console.log(`Codul de verificare introdus este: ${code}`);
  };

  const handleResendCode = () => {
    const newCode = generateRandomCode(); 
    console.log(`New code generated: ${newCode}`);

  };

  const isEmpty = verificationCodes.some(code => code === '');


  return (
    <Box>
       <Header text="Verify Code" />
      
      <Typography sx={{ fontSize: '15px', marginTop: '10px', marginBottom: '2px' }} variant="h6" className="password-heading">
       Please enter the code we just sent to your email
      </Typography>

      <form onSubmit={handleSubmit} className="verify-code-inputs">
        {verificationCodes.map((code, index) => (
          <input
            key={index}
            type="number"
            value={code}
            onChange={(e) => handleCodeChange(index, e.target.value)}
            maxLength={1}
            placeholder="-"
            required
            className="verify-code-input" 
          />
        ))}
      </form>

      <Box className="additional-content">
        <Typography sx={{ fontSize: '15px', marginTop: '10px', marginBottom: '2px' }} variant="h6" className="password-heading">
        Didn't receive OTP?
      </Typography>
      </Box>

      <Box>
        <button className='form-button-transparent' onClick={handleResendCode}>Resend code</button>
      </Box>
    
      <Box>
          <button 
           onClick={() => navigate("/new-password")}
          className="form-button"
           type="submit" 
           disabled={isEmpty}>Verify</button>
      </Box>
      
    </Box>
  );
};

export default VerifyCode;



