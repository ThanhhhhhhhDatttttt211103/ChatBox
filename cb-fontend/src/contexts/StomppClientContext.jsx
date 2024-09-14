import React, { createContext, useState, useContext } from 'react';

const StompClientContext = createContext(null);

export const StompClientProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);

  return (
    <StompClientContext.Provider value={{ stompClient, setStompClient }}>
      {children}
    </StompClientContext.Provider>
  );
};
  
export const useStompClient = () => {
  return useContext(StompClientContext);
};
