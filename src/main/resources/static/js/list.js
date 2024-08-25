document.addEventListener('DOMContentLoaded', () => {
    const checkboxes = document.querySelectorAll('.itemCheckbox');
    const selectAllCheckbox = document.getElementById('selectAll');
    const actionToolbar = document.getElementById('actionToolbar');

    const updateActionToolbar = () => {
        actionToolbar.style.display = Array.from(checkboxes).some(checkbox => checkbox.checked) ? 'block' : 'none';
    };

    checkboxes.forEach(checkbox => checkbox.addEventListener('change', updateActionToolbar));
    selectAllCheckbox.addEventListener('change', () => {
        checkboxes.forEach(checkbox => checkbox.checked = selectAllCheckbox.checked);
        updateActionToolbar();
    });

    document.getElementById('editButton').addEventListener('click', () => alert('Edit button clicked'));
    document.getElementById('deleteButton').addEventListener('click', () => alert('Delete button clicked'));

    document.getElementById('createButton').addEventListener('click' , function (){
        window.location.href= "/home/create";
    })
    function showDropdown(element) {
        const selectElement = element.nextElementSibling;
        selectElement.style.display = selectElement.style.display === 'none' ? 'block' : 'none';
    }

    // function updateStatus(selectElement, bugId) {
    //     const newStatus = selectElement.value;
    //
    //     // Gửi yêu cầu cập nhật đến server
    //     fetch('/updateStatus', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json',
    //         },
    //         body: JSON.stringify({
    //             id: bugId,
    //             status: newStatus
    //         }),
    //     })
    //         .then(response => response.json())
    //         .then(data => {
    //             // Xử lý dữ liệu trả về nếu cần
    //         })
    //         .catch((error) => {
    //             console.error('Error:', error);
    //         });
    // }

});

// document.addEventListener('DOMContentLoaded', () => {
//     function updateField(element, field, newValue) {
//         const bugId = element.closest('tr').querySelector('input.itemCheckbox').dataset.bugId;
//
//         console.log("bug id " + bugId);
//         fetch('/home/update', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify({
//                 id: bugId,
//                 field: field,
//                 value: newValue
//             }),
//         })
//             .then(response => response.json())
//             .then(data => {
//                 element.textContent = newValue;
//             })
//             .catch((error) => {
//                 console.error('Error:', error);
//                 element.textContent = newValue;
//             });
//     }
// });
