(function () {
  'use strict';

  const user = JSON.parse(localStorage.getItem('user') || 'null');

  function getUser() {
    return JSON.parse(localStorage.getItem('user') || 'null');
  }

  async function restoreSession() {
    const stored = getUser();
    if (!stored) return null;

    const sessionRes = await fetch('/api/users/session', { credentials: 'include' });
    if (sessionRes.ok) return await sessionRes.json();

    const res = await fetch('/api/users/restore-session', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ userId: stored.userId, email: stored.email })
    });
    if (res.ok) {
      const user = await res.json();
      localStorage.setItem('user', JSON.stringify(user));
      return user;
    }
    localStorage.removeItem('user');
    return null;
  }

  async function requireSeller() {
    const user = await restoreSession();
    if (!user) {
      window.location.href = 'login.html?redirect=add-product.html';
      return null;
    }
    if (user.userType !== 'seller') {
      alert('Only seller accounts can add products.');
      window.location.href = 'index.html';
      return null;
    }
    return user;
  }

  function renderHeader() {
    const el = document.getElementById('app-header');
    if (!el) return;

    const u = getUser();
    const accountLabel = u ? u.name.split(' ')[0] : 'Sign In';
    const accountSub = u ? 'Account & Lists' : 'New customer?';

    el.innerHTML = `
      <div class="top-bar">
        <div class="container">
          <span>📍 Deliver to India</span>
          <div style="display:flex;gap:16px;">
            <a href="product-list.html">Today's Deals</a>
            <a href="my-orders.html">Track Orders</a>
            ${u && u.userType === 'seller' ? '<a href="add-product.html">Sell on RetroMart</a>' : '<a href="register.html">Become a Seller</a>'}
          </div>
        </div>
      </div>
      <header class="site-header">
        <div class="header-inner">
          <a href="index.html" class="logo">
            <span class="logo-icon">🛍️</span>
            <span>
              RetroMart
              <span class="logo-tagline">Explore Plus</span>
            </span>
          </a>
          <form class="search-bar" id="globalSearchForm" action="/product-list.html" method="get">
            <input type="text" name="q" id="searchInput" placeholder="Search for products, brands and more" autocomplete="off" />
            <button type="submit" aria-label="Search">🔍</button>
          </form>
          <div class="header-actions">
            <a href="${u ? 'my-orders.html' : 'login.html'}" class="header-action">
              <span class="label">Returns</span>
              <span class="value">& Orders</span>
            </a>
            <a href="${u ? 'profile.html' : 'login.html'}" class="header-action" id="accountBtn">
              <span class="label">Hello, ${accountLabel}</span>
              <span class="value">${u ? 'Profile ▾' : 'Sign In'}</span>
            </a>
            ${u ? '<button class="header-action" id="logoutBtn" style="color:#fff;"><span class="header-action-icon">🚪</span></button>' : ''}
          </div>
        </div>
        <nav class="nav-secondary">
          <div class="container">
            <a href="/product-list.html" class="nav-link" data-nav="all">☰ All Products</a>
            <a href="/product-list.html?category=electronics" class="nav-link" data-nav="electronics">Electronics</a>
            <a href="/product-list.html?category=fashion" class="nav-link" data-nav="fashion">Fashion</a>
            <a href="/product-list.html?category=home" class="nav-link" data-nav="home">Home & Kitchen</a>
            <a href="/product-list.html?category=books" class="nav-link" data-nav="books">Books</a>
            <a href="/product-list.html?category=sports" class="nav-link" data-nav="sports">Sports</a>
            ${u && u.userType === 'seller' ? '<a href="add-product.html" class="nav-link">➕ Add Product</a>' : ''}
            ${u && u.userType === 'admin' ? '<a href="admin-dashboard.html" class="nav-link">⚙️ Admin</a>' : ''}
          </div>
        </nav>
      </header>
    `;

    const params = new URLSearchParams(window.location.search);
    const q = params.get('q');
    const searchInput = document.getElementById('searchInput');
    if (searchInput && q) searchInput.value = q;

    document.getElementById('globalSearchForm')?.addEventListener('submit', (e) => {
      const keyword = searchInput?.value.trim();
      if (!keyword) {
        e.preventDefault();
        window.location.href = '/product-list.html';
      }
    });

    highlightActiveNav();

    document.getElementById('logoutBtn')?.addEventListener('click', () => {
      localStorage.removeItem('user');
      fetch('/api/users/logout', { method: 'POST' }).finally(() => {
        window.location.href = 'login.html';
      });
    });
  }

  function highlightActiveNav() {
    const params = new URLSearchParams(window.location.search);
    const category = params.get('category');
    const isProductList = window.location.pathname.includes('product-list');

    document.querySelectorAll('.nav-link[data-nav]').forEach(link => {
      link.classList.remove('active');
      if (!isProductList) return;
      if (category && link.dataset.nav === category) {
        link.classList.add('active');
      } else if (!category && !params.get('q') && link.dataset.nav === 'all') {
        link.classList.add('active');
      }
    });
  }

  function renderFooter() {
    const el = document.getElementById('app-footer');
    if (!el) return;

    el.innerHTML = `
      <footer class="site-footer">
        <div class="footer-grid">
          <div class="footer-col">
            <h4>Get to Know Us</h4>
            <a href="#">About RetroMart</a>
            <a href="#">Careers</a>
            <a href="#">Press Releases</a>
          </div>
          <div class="footer-col">
            <h4>Connect with Us</h4>
            <a href="#">Facebook</a>
            <a href="#">Twitter</a>
            <a href="#">Instagram</a>
          </div>
          <div class="footer-col">
            <h4>Make Money with Us</h4>
            <a href="register.html">Sell on RetroMart</a>
            <a href="add-product.html">Add Products</a>
            <a href="#">Advertise Your Products</a>
          </div>
          <div class="footer-col">
            <h4>Let Us Help You</h4>
            <a href="profile.html">Your Account</a>
            <a href="my-orders.html">Your Orders</a>
            <a href="#">Returns Centre</a>
            <a href="#">Help</a>
          </div>
        </div>
        <div class="footer-bottom">
          © ${new Date().getFullYear()} RetroMart. All rights reserved. | Inspired by Flipkart & Amazon
        </div>
      </footer>
    `;
  }

  function fakeRating(productId) {
    const seed = productId * 7 + 13;
    const rating = (3.5 + (seed % 15) / 10).toFixed(1);
    const count = 50 + (seed % 950);
    return { rating, count };
  }

  function fakeDiscount(price) {
    const pct = 10 + (Math.floor(price) % 40);
    const mrp = Math.round(price / (1 - pct / 100));
    return { mrp, pct };
  }

  function renderStars(rating) {
    const full = Math.floor(rating);
    const half = rating - full >= 0.5;
    let stars = '';
    for (let i = 0; i < full; i++) stars += '★';
    if (half) stars += '★';
    while (stars.length < 5) stars += '☆';
    return stars;
  }

  async function fetchProductImage(productId) {
    try {
      const res = await fetch(`/api/images/product/${productId}`);
      const imgs = await res.json();
      return imgs.length ? imgs[0].imageUrl : '';
    } catch {
      return '';
    }
  }

  function buildProductCard(p, imgUrl) {
    const { rating, count } = fakeRating(p.productId);
    const { mrp, pct } = fakeDiscount(p.price);
    const card = document.createElement('a');
    card.className = 'product-card';
    card.href = `product-details.html?pid=${p.productId}`;
    card.innerHTML = `
      ${pct >= 20 ? `<span class="badge">${pct}% off</span>` : ''}
      <img src="${imgUrl || 'https://via.placeholder.com/300x200?text=No+Image'}" alt="${p.title}" loading="lazy" />
      <div class="title">${p.title}</div>
      <div class="rating">
        <span class="stars">${renderStars(parseFloat(rating))}</span>
        <span class="rating-count">(${count})</span>
      </div>
      <div class="price-row">
        <span class="price">₹${Number(p.price).toLocaleString('en-IN')}</span>
        <span class="mrp">₹${mrp.toLocaleString('en-IN')}</span>
        <span class="discount">${pct}% off</span>
      </div>
    `;
    return card;
  }

  async function loadProductsIntoGrid(gridEl, products) {
    gridEl.innerHTML = '';
    if (!products.length) {
      gridEl.innerHTML = '<div class="empty-state" style="grid-column:1/-1;"><div class="empty-icon">🔍</div><p>No products found. Try a different search.</p></div>';
      return;
    }
    for (const p of products) {
      const imgUrl = await fetchProductImage(p.productId);
      gridEl.appendChild(buildProductCard(p, imgUrl));
    }
  }

  const ORDER_STEPS = [
    { key: 'ordered', label: 'Ordered', icon: '🛒' },
    { key: 'confirmed', label: 'Confirmed', icon: '✓' },
    { key: 'packed', label: 'Packed', icon: '📦' },
    { key: 'shipped', label: 'Shipped', icon: '🚚' },
    { key: 'delivered', label: 'Delivered', icon: '🏠' }
  ];

  function getOrderStepIndex(order) {
    const paid = (order.paymentStatus || '').toLowerCase() === 'paid';
    const delivery = (order.deliveryStatus || 'processing').toLowerCase();
    if (!paid) return 2;
    if (delivery === 'delivered') return 5;
    if (delivery === 'shipped') return 4;
    return 3;
  }

  function getStepStatus(stepNum, currentStep) {
    if (stepNum < currentStep) return 'completed';
    if (stepNum === currentStep) return currentStep === 5 ? 'completed' : 'active';
    return 'pending';
  }

  function renderOrderTracker(order) {
    const currentStep = getOrderStepIndex(order);
    const orderDate = order.orderDate ? new Date(order.orderDate) : null;

    return `
      <div class="order-tracker">
        ${ORDER_STEPS.map((step, i) => {
          const stepNum = i + 1;
          const status = getStepStatus(stepNum, currentStep);
          let dateHint = '';
          if (stepNum === 1 && orderDate) {
            dateHint = orderDate.toLocaleDateString('en-IN', { day: 'numeric', month: 'short' });
          } else if (status === 'completed' && orderDate && stepNum > 1) {
            const d = new Date(orderDate);
            d.setDate(d.getDate() + (stepNum - 1));
            dateHint = d.toLocaleDateString('en-IN', { day: 'numeric', month: 'short' });
          }
          return `
            <div class="tracker-step ${status}">
              <div class="tracker-dot">${status === 'completed' ? '✓' : step.icon}</div>
              <div class="tracker-label">${step.label}</div>
              ${dateHint ? `<div class="tracker-date">${dateHint}</div>` : ''}
            </div>
          `;
        }).join('')}
      </div>
    `;
  }

  function buildOrderCardHtml(order, product, imgUrl) {
    const paid = (order.paymentStatus || '').toLowerCase() === 'paid';
    return `
      <div class="order-card-full">
        <div class="order-card-header">
          <div>
            <strong>Order #${order.orderId}</strong>
            <div class="order-card-meta">Placed on ${new Date(order.orderDate).toLocaleDateString('en-IN', { weekday: 'short', day: 'numeric', month: 'short', year: 'numeric' })}</div>
          </div>
          <div style="display:flex;gap:8px;align-items:center;">
            <span class="status-badge ${order.paymentStatus.toLowerCase()}">${order.paymentStatus}</span>
            ${paid ? `<span class="status-badge ${order.deliveryStatus.toLowerCase()}">${order.deliveryStatus}</span>` : ''}
          </div>
        </div>
        ${renderOrderTracker(order)}
        <div class="order-card-body">
          <img src="${imgUrl || 'https://via.placeholder.com/80?text=No+Image'}" alt="${product.title}" />
          <div>
            <strong>${product.title}</strong>
            <div class="order-card-meta">₹${Number(product.price).toLocaleString('en-IN')}</div>
            <div class="order-card-meta">📍 ${order.shippingAddress || '—'}</div>
            <div class="order-card-meta">📞 ${order.contactPhone || '—'}</div>
          </div>
          <div style="text-align:right;font-size:13px;color:var(--text-muted);">
            <div>${order.paymentMode || '—'}</div>
            ${order.transactionId ? `<div>Txn: ${order.transactionId.slice(0, 14)}…</div>` : '<div style="color:var(--warning);">Payment pending</div>'}
          </div>
        </div>
        ${!paid ? `
          <div style="margin-top:16px;padding-top:16px;border-top:1px solid var(--border-light);display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:12px;">
            <span style="font-size:14px;color:var(--text-muted);">Complete payment to confirm your order</span>
            <a href="payment.html?orderId=${order.orderId}&amount=${product.price}" class="btn btn-primary">Pay Now · ₹${Number(product.price).toLocaleString('en-IN')}</a>
          </div>
        ` : `
          <div style="margin-top:16px;padding-top:16px;border-top:1px solid var(--border-light);text-align:right;">
            <a href="receipt.html?orderId=${order.orderId}" class="btn btn-outline">Download Receipt</a>
          </div>
        `}
      </div>
    `;
  }

  window.RetroMart = {
    getUser,
    restoreSession,
    requireSeller,
    fakeRating,
    fakeDiscount,
    renderStars,
    fetchProductImage,
    buildProductCard,
    loadProductsIntoGrid,
    renderOrderTracker,
    buildOrderCardHtml,
    getOrderStepIndex
  };

  document.addEventListener('DOMContentLoaded', () => {
    renderHeader();
    renderFooter();
  });
})();
