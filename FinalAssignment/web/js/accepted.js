function openModal(name, dep, from, to, reason) {
        document.getElementById(
          "modal-user"
        ).innerHTML = `User: <strong>${name}</strong>, Dep: <strong>${dep}</strong>`;
        document.getElementById("modal-from").value = from;
        document.getElementById("modal-to").value = to;
        document.getElementById("modal-reason").value = reason;
        document.getElementById("modal").style.display = "flex";
      }

      function closeModal() {
        document.getElementById("modal").style.display = "none";
      }

      // Cho phép bấm ngoài modal để đóng
      document.getElementById("modal").addEventListener("click", function (e) {
        if (e.target === this) closeModal();
      });


