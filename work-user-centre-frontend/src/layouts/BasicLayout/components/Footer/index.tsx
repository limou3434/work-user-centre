// ./src/layouts/BasicLayout/components/Footer/index.tsx: 网页页脚组件

"use client";

import "./index.css";

const Footer = () => {
  const currentYear = new Date().getFullYear();
  return (
    <div className="footer">
      <div>© {currentYear} Made</div>
      <div>by Work</div>
    </div>
  );
};

export default Footer;
