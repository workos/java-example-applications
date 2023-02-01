@param com.workos.organizations.models.OrganizationList organizations

!{var before = organizations.listMetadata.before;}
!{var after = organizations.listMetadata.after;}

<link rel="stylesheet" href="main.css">

<body class='flex'>
<div class="logged_in_div_left flex_column">
  <div class="flex logged_in_div_left_title">
    <div class="flex-column">
      <div class="flex space-between width-11vw">
        <div><h2>Audit Logs</h2></div>
      </div>
      <div><p class="sub-heading">Java Example App</p></div>
    </div>
  </div>
  <hr style="height:0.5px;width:75%;">
  <div class="flex flex_column success-buttons">
    <a href="/logout">
      <div class="flex sidebar-button selected">
        <div><img class="org-icon" src="/images/icon-settings.svg" alt="settings icon"></div>
        <div>
          <p>Organizations</p>
        </div>
      </div>
    </a>
    <div id="myBtn" class="flex sidebar-button">
      <div><img class="org-icon" src="/images/icon-document.svg" alt="audit logs icon"></div>
      <div>
        <p>Audit Logs</p>
      </div>
    </div>
  </div>
</div>

<div class="logged_in_div_right">
  <div class="logged_in_nav">
    <div class="flex">
      <div>
        <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
      </div>
      <div>
        <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
            Reference</button></a>
      </div>
      <div>
        <a href="https://workos.com/blog" target="_blank"><button
                  class='button nav-item blog-nav-button'>Blog</button></a>
      </div>
      <div>
        <a href="https://workos.com/" target="_blank">
          <img class="nav-image" src="/images/workos_favicon.png" alt="link to workos.com">
        </a>
      </div>
    </div>
  </div>

  <div class='flex_column directory-card height-70vh'>
    <div class="flex width-70vw space-between">
      <div>
        <h2>Select a Directory</h2>
      </div>
    </div>

    <div class='flex_column card width-65vw'>
      <div>
        <table class="width-65vw">
          <tr>
            <th>Organization</th>
            <th>ID</th>
            <th>View Settings</th>
          </tr>
          @for(var organization : organizations.data)
            <tr>
              <td>${organization.name}</td>
              <td>${organization.id}</td>
              <td><a class="icon-button" href="/set_org?id=${organization.id}">
                  <img class="org-icon" src="/images/icon-settings.svg" alt="link to workos.com">
                </a></td>
            </tr>
          @endfor

        </table>
      </div>
      <div class="flex flex-end width-65vw">
        @if(after != null)
          <a class="button button-outline" href="?after=${after}">Before</a>
        @endif
        @if(before != null)
          <a class="button button-outline" href="?before=${before}">Next</a>
        @endif
      </div>
    </div>
  </div>

</div>



<div id="myModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <p>Please select an Organization</p>
  </div>
</div>

<script>

    var modal = document.getElementById("myModal");
    var btn = document.getElementById("myBtn");
    var span = document.getElementsByClassName("close")[0];
    btn.onclick = function() {
    modal.style.display = "block";
    }
    span.onclick = function() {
    modal.style.display = "none";
    }
    window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
    }
 </script>
</body>
